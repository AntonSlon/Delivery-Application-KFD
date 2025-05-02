package org.example.deliveryapplication.model.request

import org.example.deliveryapplication.model.courier.Vehicle

data class CustomerSelectVehicleRequest(
    val vehicle: String,
)
