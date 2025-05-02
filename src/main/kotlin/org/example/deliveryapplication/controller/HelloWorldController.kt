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
class HelloWorldController(val graphhopperService: GraphhopperService,
    val nominatimService: NominatimService, val courierDAO: CourierDAO,
    val userDao: UserDAO,
    val adminService: AdminServiceImpl
) {
    @GetMapping
    fun test(): String = "Hello World"

    @GetMapping("/routeTest")
    fun routeTest() = graphhopperService.getRoute(
            listOf("52.533825,13.431846", "52.507503,13.404037", "52.518778,13.418783"),
            "foot", "de", false)

    @GetMapping("/encodeTest")
    fun encodeTest() = nominatimService.geocodeAddress("Москва,Красная площадь")

    @Transactional
    @GetMapping("/courierCreateTest")
    fun create(){
        val user = userDao.findByEmail("sdasa346573@yandex.ru")!!
        courierDAO.save(Courier(404 ,"52.533825,13.404037", UserStatus.ONLINE.name, "", user = user))
    }

    @GetMapping("/get")
    fun getUser(@RequestParam id: Long) = adminService.getUserByID(id)

}