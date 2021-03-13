package com.dsm.kkoribyeol.security

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationConfiguration(
    private val authorizationTokenProvider: AuthorizationTokenProvider,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity?) {
        val authenticationFilter = AuthenticationFilter(authorizationTokenProvider)
        http?.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}