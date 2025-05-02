package org.example.deliveryapplication.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class  UserRegisterRequest @JsonCreator constructor(
    @JsonProperty("name")
    @field:NotBlank(message = "Name is required")
    val name: String,

    @JsonProperty("email")
    @field:Email(message = "Incorrect email ")
    @field:NotBlank(message = "Email is required")
    val email: String,

    @JsonProperty("password")
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Short password, min 6 symbols")
    val password: String,

//    @JsonProperty("telephoneNumber")
//    @field:NotBlank(message = "Number is required")
//    val telephoneNumber: String,

)
