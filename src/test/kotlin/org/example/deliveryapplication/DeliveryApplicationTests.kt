package org.example.deliveryapplication

import org.example.deliveryapplication.graphhopper.GraphhopperService
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest

class DeliveryApplicationTests {

    companion object {
        val log = LoggerFactory.getLogger(GraphhopperService::class.java)
    }


    @Test
    fun contextLoads() {
        log.info("Hello!")
        log.error("Error!")
    }

}
