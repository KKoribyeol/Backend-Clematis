package com.dsm.kkoribyeol.global.exception.entrypoint

import com.dsm.kkoribyeol.global.exception.response.CommonExceptionResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class InvalidTokenExceptionEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {

        val objectMapper = jacksonObjectMapper().findAndRegisterModules()
        val exceptionResponse = objectMapper.writeValueAsString(
            CommonExceptionResponse(
                code = "INVALID_TOKEN",
                message = "Invalid Token",
            )
        )

        response.contentType = "application/json;charset=UTF-8"
        response.characterEncoding = "UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.outputStream.println(exceptionResponse)
    }
}