package com.dsm.clematis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ClematisApplication

fun main(args: Array<String>) {
    runApplication<ClematisApplication>(*args)
}
