package org.example.deliveryapplication.service.Impl

import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.example.deliveryapplication.Exceptions.*
import org.example.deliveryapplication.config.SecurityConfig
import org.example.deliveryapplication.database.entity.Courier
import org.example.deliveryapplication.database.entity.Rating
import org.example.deliveryapplication.database.entity.User
import org.example.deliveryapplication.database.repository.CourierDAO
import org.example.deliveryapplication.database.repository.RatingDAO
import org.example.deliveryapplication.database.repository.TempStorage
import org.example.deliveryapplication.database.repository.UserDAO
import org.example.deliveryapplication.model.UserStatus
import org.example.deliveryapplication.model.request.UserLoginRequest
import org.example.deliveryapplication.model.request.UserRegisterRequest
import org.example.deliveryapplication.model.response.AuthCodeResponse
import org.example.deliveryapplication.model.response.UserLoginResponse
import org.example.deliveryapplication.model.response.UserRegisterResponse
import org.example.deliveryapplication.security.JwtParser
import org.example.deliveryapplication.service.UserAuthService
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class UserAuthServiceAuthImpl(
    private val userDAO: UserDAO,
    private val mailSenderServiceImpl: MailSenderServiceImpl,
    private val tempStorage: TempStorage,
    private val jwtParser: JwtParser,
    private val httpServletResponse: HttpServletResponse,
    private val courierDAO: CourierDAO,
    private val securityConfig: SecurityConfig,
    private val getEncoder: PasswordEncoder,
    private val ratingDAO: RatingDAO
): UserAuthService{
    override fun register(request: UserRegisterRequest): UserRegisterResponse {

        val user = User(name = request.name, email = request.email,
             password = getEncoder.encode(request.password))

        if (userDAO.existsByEmail(user.email))
            throw UserExistException()

        mailSenderServiceImpl.sendAuthorizationCode(user.email)
        tempStorage.addAuthCode(mailSenderServiceImpl.uuid, user)

        return UserRegisterResponse(name = user.name, email = user.email,
            id = user.id, status = "Pending")
    }

    override fun confirmRegister(uuid: String): AuthCodeResponse {
        if (tempStorage.getUser(uuid) != null)
            userDAO.save(tempStorage.getUser(uuid)!!)
        return AuthCodeResponse(status = "Success registration.")
    }

    @Transactional
    override fun login(request: UserLoginRequest): UserLoginResponse {
        if (!userDAO.existsByEmail(request.email))
            throw UserDoesntExistException()

        if (!getEncoder.matches(request.password ,userDAO.findByEmail(request.email)?.password))
            throw IncorrectPasswordException()

        val user = userDAO.findByEmail(request.email)

        if (request.role == "COURIER"){
            val courier = courierDAO.findById(user!!.id).getOrNull()
            println(courier)
            if (courier == null){
                println(user.id)
                println(Courier(user.id, "52.533825,13.404037", UserStatus.ONLINE.name, "", user = user))
                val courier1 = Courier(
                    currentLocation = "52.533825,13.404037",
                    status = UserStatus.ONLINE.name,
                    vehicle = "",
                    user = user
                )
                courier1.status = UserStatus.ONLINE.name
                courierDAO.save(courier1)
                val rating = Rating(courier = courier1)
                ratingDAO.save(rating)
            }else {
                courier.status = UserStatus.ONLINE.name
                courierDAO.save(courier)
            }
        }

        user!!.role = request.role
        userDAO.save(user)

        val token = jwtParser.generateJwt(request.email)

        jwtParser.setTokenInCookie(httpServletResponse, token)
        return UserLoginResponse(status = "200", "Success logged in")
    }

}