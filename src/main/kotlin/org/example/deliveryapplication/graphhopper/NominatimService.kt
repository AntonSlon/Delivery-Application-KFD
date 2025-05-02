package org.example.deliveryapplication.graphhopper

import com.fasterxml.jackson.databind.util.JSONPObject
import netscape.javascript.JSObject
import org.example.deliveryapplication.Exceptions.ApiError
import org.example.deliveryapplication.model.response.GeocodeResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.web.util.UriUtils
import java.net.URI
import java.net.http.HttpHeaders

@Service
class NominatimService(val nominatimClient: NominatimClient) {

    fun geocodeAddress(address: String): Array<GeocodeResponse> =
          nominatimClient.getCoordinates(address, "json", "1")
}
