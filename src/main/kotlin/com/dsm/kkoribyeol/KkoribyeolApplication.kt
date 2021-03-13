package com.dsm.kkoribyeol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
@EnableJpaAuditing
@SpringBootApplication
class KkoribyeolApplication

fun main(args: Array<String>) {
    runApplication<KkoribyeolApplication>(*args)
}
