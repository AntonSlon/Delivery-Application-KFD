package org.example.deliveryapplication.service

interface MailSenderService {
    fun sendAuthorizationCode(mail: String)
}