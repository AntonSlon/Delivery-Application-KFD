package org.example.deliveryapplication.graphhopper

import org.example.deliveryapplication.model.response.RouteResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.awt.Point

@Component
@FeignClient(name = "Graphhopper", url = "http://localhost:8989")
interface GraphhopperClient {
    @GetMapping("/route")
    fun getRoute(
        @RequestParam("point") points: List<String>,
        @RequestParam("profile") profile: String,
        @RequestParam("locale") locale: String,
        @RequestParam("calc_points") calcPoints: Boolean,
    ): RouteResponse
}