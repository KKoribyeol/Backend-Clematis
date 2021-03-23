package com.dsm.kkoribyeol.global.security.configuration

import com.dsm.kkoribyeol.global.security.filter.AuthenticationFilter
import com.dsm.kkoribyeol.global.exception.entrypoint.InvalidTokenExceptionEntryPoint
import com.dsm.kkoribyeol.global.security.provider.AuthenticationProvider
import com.dsm.kkoribyeol.global.security.provider.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    private val invalidTokenExceptionEntryPoint: InvalidTokenExceptionEntryPoint,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun authenticationFilter() = AuthenticationFilter(tokenProvider)

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authenticationProvider)
            .passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        http
            .cors()
                .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(invalidTokenExceptionEntryPoint)
                .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/account").permitAll()
                .antMatchers(HttpMethod.POST, "/account/login").permitAll()
                .anyRequest().authenticated()

        http
            .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/**/swagger-ui.html/**", "/webjars/**", "/swagger/**", "/v2/api-docs", "/swagger-resources/**")
    }
}