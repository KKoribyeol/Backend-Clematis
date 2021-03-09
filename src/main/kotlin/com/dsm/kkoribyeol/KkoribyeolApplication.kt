package com.dsm.kkoribyeol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KkoribyeolApplication

fun main(args: Array<String>) {
    runApplication<KkoribyeolApplication>(*args)
}
