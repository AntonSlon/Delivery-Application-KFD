package org.example.deliveryapplication.service.Impl

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import org.example.deliveryapplication.database.entity.Order
import org.example.deliveryapplication.database.entity.Rating
import org.example.deliveryapplication.database.entity.Review
//import org.example.deliveryapplication.database.entity.Rating
import org.example.deliveryapplication.database.repository.*
import org.example.deliveryapplication.graphhopper.GraphhopperService
import org.example.deliveryapplication.model.OrderStatus
import org.example.deliveryapplication.model.PaymentMethod
import org.example.deliveryapplication.model.PaymentStatus
import org.example.deliveryapplication.model.courier.Vehicle
import org.example.deliveryapplication.model.request.CourierAcceptOrderRequest
import org.example.deliveryapplication.model.request.CourierUpdateOrderStatusRequest
import org.example.deliveryapplication.model.request.CustomerSelectVehicleRequest
import org.example.deliveryapplication.model.response.*
import org.example.deliveryapplication.service.CourierService
import org.example.deliveryapplication.util.getPrincipal
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis
import java.net.http.HttpResponse
import java.util.*

@Service
class CourierServiceImpl(
    private val userDao: UserDAO,
    private val orderDAO: OrderDAO,
    private val courierDAO: CourierDAO,
    private val graphhopperService: GraphhopperService,
    private val paymentDAO: PaymentDAO,
    private val reviewDAO: ReviewDAO,
    private val ratingDAO: RatingDAO

) : CourierService {
    override fun acceptOrder(orderId: Long): CourierAcceptOrderResponse {
        val order = orderDAO.findById(orderId).get()
        val courier = courierDAO.findById(getPrincipal().id).get()

        order.status = OrderStatus.IN_PROGRESS.name
        order.courier = courier

        graphhopperService.getRoute(
            listOf(
                courier.currentLocation,
                order.startAddress, order.endAddress
            ), courier.vehicle.lowercase(Locale.getDefault()), "de", false
        )

        orderDAO.save(order)
        return CourierAcceptOrderResponse("Заказ принят")
    }

    override fun updateOrderList(): CourierUpdateOrderListResponse {
        val orderList = orderDAO.findByStatus(OrderStatus.WAITING.name)
        val orders = mutableListOf<OrderResponse>()

        val courier = courierDAO.findById(getPrincipal().id).get()

        val vehicle = courier.vehicle.lowercase(Locale.getDefault())

        orderList.forEach { order : Order ->
            val route = graphhopperService.getRoute(
                listOf(courier.currentLocation, order.startAddress, order.endAddress),
                vehicle,
                "de",
                false
            )
            val time = route.paths[0].time

            println("$time ------------------------------------ TIME")

            if (time < 60_000) {
                orders.add(OrderResponse(order.id, order.status, order.startAddress, order.endAddress, order.comment))
            }
        }

        orders.forEach { println(it) }
        return CourierUpdateOrderListResponse(orders)
    }

    override fun completedOrderList(): CourierCompletedOrderListResponse {
        val orderList = orderDAO.findByStatusAndCourierId(OrderStatus.DELIVERED.name, getPrincipal().id)
        println("$orderList <-------------------------------------")
        val orders = mutableListOf<CourierCompletedResponse>()

        orderList.forEach { order ->
            orders.add(
                CourierCompletedResponse(
                    order.id, order.status,
                    order.startAddress, order.endAddress, order.comment, order.createdAt, order.payment.cost
                )
            )
        }

        return CourierCompletedOrderListResponse(orders)
    }

    override fun updateCurrentOrderStatus() {
        val order = orderDAO.findFirstByOrderByIdDesc()
        val user = userDao.findById(getPrincipal().id).get()

        order.status = OrderStatus.DELIVERED.name
        if (order.payment.paymentMethod == PaymentMethod.PROFILE_BALANCE.name)
            user.money += order.payment.cost

        order.payment.status = PaymentStatus.PAID.name

        paymentDAO.save(order.payment)

        orderDAO.save(order)
    }

    override fun getBalance(): Int {
        val user = userDao.findById(getPrincipal().id).get()
        return user.money
    }

    override fun getMyScore(): CourierScoreRequest{
        val rating = ratingDAO.findById(getPrincipal().id).get()
        println("$rating  <------------------------------- RATING")
        val reviews = reviewDAO.findByCourierId(getPrincipal().id)
        println("$reviews (---------------------REVIEWS")

        if (reviews.isEmpty())
            return CourierScoreRequest(rating.id, rating.totalScore, rating.count)

        reviews.forEach {
            review -> rating.count += 1
            rating.totalScore += review.rating
        }

        rating.totalScore /= reviews.size

        ratingDAO.save(rating)
        return CourierScoreRequest(rating.id, rating.totalScore, rating.count)
    }

    override fun selectVehicle(customerSelectVehicleRequest: CustomerSelectVehicleRequest) {
        val courier = courierDAO.findById(getPrincipal().id).get()
        courier.vehicle = customerSelectVehicleRequest.vehicle
        courierDAO.save(courier)
    }
}