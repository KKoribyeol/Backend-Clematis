package com.dsm.kkoribyeol.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class AuthenticationFilter(
    private val authorizationTokenProvider: AuthorizationTokenProvider,
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        SecurityContextHolder.getContext().authentication =
            authorizationTokenProvider.getAuthentication(
                authorizationToken = authorizationTokenProvider.toAuthorizationToken(
                    token = extractToken(request as HttpServletRequest)
                )
            )

        chain?.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String {
        println("request: $request")
        return request.getHeader("Authorization")
    }
}