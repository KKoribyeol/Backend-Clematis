package com.dsm.kkoribyeol.configuration

import com.dsm.kkoribyeol.security.AuthorizationTokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenConfiguration {

    @Value("\${TOKEN_SECRET_KEY:spring-security-love}")
    private var secretKey: String = ""

    @Bean
    fun authorizationTokenProvider() = AuthorizationTokenProvider(secretKey)
}