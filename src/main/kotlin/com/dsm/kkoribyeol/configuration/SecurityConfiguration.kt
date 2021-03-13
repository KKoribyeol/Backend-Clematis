package com.dsm.kkoribyeol.configuration

import com.dsm.kkoribyeol.security.AccessDeniedHandler
import com.dsm.kkoribyeol.security.AuthenticationConfiguration
import com.dsm.kkoribyeol.security.AuthenticationEntryPoint
import com.dsm.kkoribyeol.security.AuthorizationTokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfiguration(
    private val authorizationTokenProvider: AuthorizationTokenProvider,
    private val authenticationErrorHandler: AuthenticationEntryPoint,
    private val accessDeniedHandler: AccessDeniedHandler,
) : WebSecurityConfigurerAdapter() {

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .csrf().disable()

            .exceptionHandling()
            .authenticationEntryPoint(authenticationErrorHandler)
            .accessDeniedHandler(accessDeniedHandler)

            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers("/user/**").authenticated()
            .anyRequest().permitAll()

            .and()
            .apply(authenticationConfiguration())
    }

//    override fun configure(web: WebSecurity) {
//        web.ignoring()
//            .antMatchers(HttpMethod.)
//    }

    fun authenticationConfiguration() = AuthenticationConfiguration(authorizationTokenProvider)
}