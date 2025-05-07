package org.example.deliveryapplication.graphhopper

import org.example.deliveryapplication.database.repository.CourierDAO
import org.example.deliveryapplication.database.repository.OrderDAO
import org.example.deliveryapplication.database.repository.UserDAO
import org.example.deliveryapplication.model.request.CustomerCreateOrderRequest
import org.example.deliveryapplication.model.response.RouteResponse
import org.springframework.stereotype.Service

@Service
class GraphhopperService(
    val graphhopperClient: GraphhopperClient,
    val nominatimService: NominatimService,
) {


    fun getRoute(
        addresses: List<String>,
        profile: String,
        locale: String,
        calcPoints: Boolean
    ): RouteResponse {
        println("GEOCODE ADDRESS: $addresses")
        val jsonPoints = addresses.map { address -> nominatimService.geocodeAddress(address) }
        println("JSON POINTS: $jsonPoints")
        val points = jsonPoints.map { obj -> obj[0].lat + "," + obj[0].lon }
        println("POINTS: $points")

        return graphhopperClient.getRoute(points, profile, locale, calcPoints)
    }

    //колхозное вычисление, в будущем можно улучшить логику рассчета
    fun calculatePrice(customerCreateOrderRequest: CustomerCreateOrderRequest): Int {
        val calc1 = getRoute(
            listOf(customerCreateOrderRequest.startAddress, customerCreateOrderRequest.endAddress),
            "foot",
            "de",
            false
        ).paths[0].distance
        val calc2 = getRoute(
            listOf(customerCreateOrderRequest.startAddress, customerCreateOrderRequest.endAddress),
            "car",
            "de",
            false
        ).paths[0].distance
        return ((((calc1 + calc2) / 2) / 1000) * 5).toInt()
    }
}
