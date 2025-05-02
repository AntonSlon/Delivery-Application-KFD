package org.example.deliveryapplication.model.response

data class RouteResponse(
    val paths: List<Path>
)

data class Path(
    val distance: Float,
    val time: Int
)
