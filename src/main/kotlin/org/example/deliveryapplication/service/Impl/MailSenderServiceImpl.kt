package org.example.deliveryapplication.service.Impl

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator
import org.example.deliveryapplication.util.MailSenderConnector
import org.example.deliveryapplication.service.MailSenderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.util.*
import kotlin.random.Random

@Service
class MailSenderServiceImpl(val connector: MailSenderConnector) : MailSenderService {
    @Value("\${mail-sender.enabled}")
    val enabled: Boolean = true

    var uuid: String = ""
    override fun sendAuthorizationCode(mail: String) {
        uuid = UUID.randomUUID().toString()
        if (enabled) {
            val message = connector.mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setFrom("deliveryservicekfd@gmail.com")
            helper.setTo(mail)
            helper.setSubject("Your Authorization Reference")
            helper.setText(
                """
                <p>Для подтверждения почты перейдите по ссылке ниже:</p>
                <a href="http://localhost:8080/auth/confirm?codeUUID=${uuid}"></a>
                <p>If you didn't request this, please ignore this email.</p>
                """.trimIndent(), true
            )
            connector.getConnection()
            connector.mailSender.send(message)
        }
        println("$uuid в MailSender")
    }
}

