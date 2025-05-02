package org.example.deliveryapplication.service.Impl

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpSession
import org.example.deliveryapplication.Exceptions.IncorrectRatingException
import org.example.deliveryapplication.Exceptions.OrderIsNull
import org.example.deliveryapplication.database.entity.Order
import org.example.deliveryapplication.database.entity.Payment
import org.example.deliveryapplication.database.entity.Review
//import org.example.deliveryapplication.database.entity.Review
import org.example.deliveryapplication.database.repository.*
import org.example.deliveryapplication.graphhopper.GraphhopperService
import org.example.deliveryapplication.model.OrderStatus
import org.example.deliveryapplication.model.PaymentMethod
import org.example.deliveryapplication.model.PaymentStatus
import org.example.deliveryapplication.model.request.CourierUpdateOrderStatusRequest
import org.example.deliveryapplication.model.request.CustomerConfirmOrderRequest
import org.example.deliveryapplication.model.request.CustomerCreateOrderRequest
import org.example.deliveryapplication.model.request.CustomerFeedbackRequest
import org.example.deliveryapplication.model.response.CustomerConfirmOrderResponse
import org.example.deliveryapplication.model.response.CustomerCreateOrderResponse
import org.example.deliveryapplication.model.response.CustomerOrderRequest
import org.example.deliveryapplication.service.CustomerService
import org.example.deliveryapplication.util.getPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis
import java.util.*
import kotlin.math.abs

@Service
class CustomerServiceImpl(
    private val orderDAO: OrderDAO,
    private val graphhopperService: GraphhopperService,
    private val userDao: UserDAO,
    private val reviewDAO: ReviewDAO,
    private val courierDAO: CourierDAO,
    private val paymentDAO: PaymentDAO,
) : CustomerService {
    override fun createOrder(
        customerCreateOrderRequest: CustomerCreateOrderRequest
    ): CustomerCreateOrderResponse {
        val principal = getPrincipal().username
        val customer = userDao.findByEmail(principal)

        val price = graphhopperService.calculatePrice(customerCreateOrderRequest)

        val payment = Payment()
        payment.cost = price
        paymentDAO.save(payment)

        val order = Order(
            status = OrderStatus.WAITING_FOR_CONFIRM.name,
            startAddress = customerCreateOrderRequest.startAddress,
            endAddress = customerCreateOrderRequest.endAddress,
            comment = customerCreateOrderRequest.comment,
            customer = customer!!,
            courier = null,
            payment = payment
        )

        orderDAO.save(order)

        return CustomerCreateOrderResponse("Подтвердите заказ и выберите средство оплаты", price)
    }


    override fun confirmOrder(customerConfirmOrderRequest: CustomerConfirmOrderRequest): CustomerConfirmOrderResponse {
        val order = orderDAO.findByStatus(OrderStatus.WAITING_FOR_CONFIRM.name)[0]
        println(orderDAO.findByStatus(OrderStatus.WAITING_FOR_CONFIRM.name))
        println("${order.payment} СТОИМОСТЬ ЗАКАЗА ИЗ БД ________________________")
        val user = userDao.findById(getPrincipal().id).get()

        if (customerConfirmOrderRequest.paymentMethod == PaymentMethod.PROFILE_BALANCE.name) {
            if (user.money < order.payment.cost) return CustomerConfirmOrderResponse("Недостаточно средств на балансе")
            user.money -= order.payment.cost
            order.payment.paymentMethod = PaymentMethod.PROFILE_BALANCE.name
            order.payment.status = PaymentStatus.PAID.name
        }
        else {
            order.payment.paymentMethod = PaymentMethod.CASH.name
            order.payment.status = PaymentStatus.NOT_PAID.name
        }
        order.status = OrderStatus.WAITING.name
        paymentDAO.save(order.payment)

        userDao.save(user)
        orderDAO.save(order)
        return CustomerConfirmOrderResponse("Заказ успешно создан")
    }

    override fun feedbackLastOrder(customerFeedbackRequest: CustomerFeedbackRequest) {
        val order = orderDAO.findByCustomerIdAndStatus(getPrincipal().id, OrderStatus.DELIVERED.name) // orderList

        if ((customerFeedbackRequest.value < 1) and (customerFeedbackRequest.value > 5))
            throw IncorrectRatingException()

        if (order.isEmpty()) throw OrderIsNull()

        val review = Review(
            comment = customerFeedbackRequest.comment, order = order.last(),
            rating = customerFeedbackRequest.value, courierId = order.last().courier!!.id
        )

        reviewDAO.save(review)
    }

    override fun cancelOrder() {
        val order = orderDAO.findByCustomerIdAndStatus(getPrincipal().id, OrderStatus.WAITING.name)[0]
        val user = userDao.findById(getPrincipal().id).get()

        if (order.payment.paymentMethod == PaymentMethod.PROFILE_BALANCE.name)
            user.money += order.payment.cost

        userDao.save(user)
        order.status = OrderStatus.CANCELED.name // переводить в Cancelled, а не удалять
        orderDAO.save(order)
    }

//    override fun updateOrder(updateOrderStatusRequest: CourierUpdateOrderStatusRequest) {
//        val order = orderDAO.findByCustomerId(getPrincipal().id)
//        order.status = updateOrderStatusRequest.status
//        orderDAO.save(order)
//    }

    override fun getCurrentOrder(): CustomerOrderRequest {
        val order = orderDAO.findByCustomerIdAndStatus(getPrincipal().id, OrderStatus.IN_PROGRESS.name)

        if (order.isEmpty()) {
            throw OrderIsNull()
        } else return CustomerOrderRequest(
            order[0].id, order[0].status, order[0].startAddress,
            order[0].endAddress, order[0].comment, order[0].createdAt
        )
    }
}
