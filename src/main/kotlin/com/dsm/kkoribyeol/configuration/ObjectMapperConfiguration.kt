package com.dsm.kkoribyeol.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().findAndRegisterModules()
}