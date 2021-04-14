package com.dsm.clematis.global.configuration

import com.dsm.clematis.domain.push.external.FirebaseConnection
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
class FirebaseConfiguration(
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun firebaseConnection() =
        Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
            .create(FirebaseConnection::class.java)
}