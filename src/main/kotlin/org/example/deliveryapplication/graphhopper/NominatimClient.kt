package org.example.deliveryapplication.graphhopper

import org.example.deliveryapplication.model.response.GeocodeResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Component
@FeignClient(value = "Nominatim", url = "https://nominatim.openstreetmap.org")
interface NominatimClient {
    @GetMapping("/search")
    fun getCoordinates(
        @RequestParam("q") query: String,
        @RequestParam("format") format: String,
        @RequestParam("addressdetails") addressDetails: String,
    ): Array<GeocodeResponse>
}