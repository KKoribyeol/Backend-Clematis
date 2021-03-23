package com.dsm.kkoribyeol.global.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class TestPasswordEncoderConfiguration {

    @Primary
    @Bean
    fun testPasswordEncoder() =
        object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence) =
                rawPassword.toString()

            override fun matches(rawPassword: CharSequence, encodedPassword: String) =
                rawPassword == encodedPassword

            override fun upgradeEncoding(encodedPassword: String) = false
        }
}