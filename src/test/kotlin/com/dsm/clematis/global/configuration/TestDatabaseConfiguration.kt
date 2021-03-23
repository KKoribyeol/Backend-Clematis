package com.dsm.clematis.global.configuration

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class TestDatabaseConfiguration {
    val testDriverClassName = "org.h2.Driver"
    val testUrl = "jdbc:h2:mem:testdb;MODE=mysql"
    val testUsername = "sa"
    val testPassword = ""

    @Primary
    @Bean
    fun testDataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(testDriverClassName)
            .url(testUrl)
            .username(testUsername)
            .password(testPassword)
            .build()
}