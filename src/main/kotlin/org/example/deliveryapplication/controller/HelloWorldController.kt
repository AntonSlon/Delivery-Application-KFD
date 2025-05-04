package org.example.deliveryapplication.controller

import jakarta.transaction.Transactional
import org.example.deliveryapplication.database.entity.Courier
import org.example.deliveryapplication.database.repository.CourierDAO
import org.example.deliveryapplication.database.repository.OrderDAO
import org.example.deliveryapplication.database.repository.UserDAO
import org.example.deliveryapplication.graphhopper.GraphhopperService
import org.example.deliveryapplication.graphhopper.NominatimService
import org.example.deliveryapplication.model.UserStatus
import org.example.deliveryapplication.model.response.GeocodeResponse
import org.example.deliveryapplication.service.AdminService
import org.example.deliveryapplication.service.Impl.AdminServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class HelloWorldController {
    @GetMapping
    fun test(): String = "Hello World"
}
