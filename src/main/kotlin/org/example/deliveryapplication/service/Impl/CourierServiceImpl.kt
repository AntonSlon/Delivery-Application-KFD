package org.example.deliveryapplication.service.Impl

//import org.example.deliveryapplication.database.entity.Rating
import org.example.deliveryapplication.Exceptions.CourierStatusException
import org.example.deliveryapplication.database.entity.Order
import org.example.deliveryapplication.database.repository.*
import org.example.deliveryapplication.graphhopper.GraphhopperService
import org.example.deliveryapplication.model.OrderStatus
import org.example.deliveryapplication.model.PaymentMethod
import org.example.deliveryapplication.model.PaymentStatus
import org.example.deliveryapplication.model.UserStatus
import org.example.deliveryapplication.model.request.CourierScoreRequest
import org.example.deliveryapplication.model.request.CustomerSelectVehicleRequest
import org.example.deliveryapplication.model.response.*
import org.example.deliveryapplication.service.CourierService
import org.example.deliveryapplication.util.getPrincipal
import org.springframework.stereotype.Service
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
        val order = orderDAO.findByIdAndStatus(orderId, OrderStatus.WAITING.name)
        val courier = courierDAO.findById(getPrincipal().id).get()

        if (courier.status == UserStatus.BUSY.name)
            throw CourierStatusException()

        courier.status = UserStatus.BUSY.name

        order.status = OrderStatus.IN_PROGRESS.name
        order.courier = courier

        graphhopperService.getRoute(
            listOf(
                courier.currentLocation,
                order.startAddress, order.endAddress
            ), courier.vehicle.lowercase(Locale.getDefault()), "de", false
        )

        courierDAO.save(courier)
        orderDAO.save(order)
        return CourierAcceptOrderResponse("Заказ принят")
    }

    override fun updateOrderList(): CourierUpdateOrderListResponse {
        val orderList = orderDAO.findByStatus(OrderStatus.WAITING.name)
        val orders = mutableListOf<OrderResponse>()
        val courier = courierDAO.findById(getPrincipal().id).get()
        val vehicle = courier.vehicle.lowercase(Locale.getDefault())

        orderList.forEach { order: Order ->
            val route = graphhopperService.getRoute(
                listOf(courier.currentLocation, order.startAddress, order.endAddress),
                vehicle,
                "de",
                false
            )
            val time = route.paths[0].time

            if (time < 4_200_000) {
                orders.add(OrderResponse(order.id, order.status, order.startAddress, order.endAddress, order.comment))
            }
        }

        orders.forEach { println(it) }
        return CourierUpdateOrderListResponse(orders)
    }

    override fun getCompletedOrderList(): CourierCompletedOrderListResponse {
        val orderList = orderDAO.findByStatusAndCourierId(OrderStatus.DELIVERED.name, getPrincipal().id)
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
        val courier = courierDAO.findById(getPrincipal().id).get()

        order.status = OrderStatus.DELIVERED.name
        if (order.payment.paymentMethod == PaymentMethod.PROFILE_BALANCE.name)
            user.money += order.payment.cost

        order.payment.status = PaymentStatus.PAID.name
        courier.status = UserStatus.ONLINE.name

        paymentDAO.save(order.payment)
        courierDAO.save(courier)

        orderDAO.save(order)
    }

    override fun getBalance(): Int {
        val user = userDao.findById(getPrincipal().id).get()
        return user.money
    }

    override fun getMyScore(): CourierScoreRequest {
        val rating = ratingDAO.findById(getPrincipal().id).get()
        val reviews = reviewDAO.findByCourierId(getPrincipal().id)

        if (reviews.isEmpty())
            return CourierScoreRequest(rating.id, rating.totalScore, rating.count)

        reviews.forEach { review ->
            rating.count += 1
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