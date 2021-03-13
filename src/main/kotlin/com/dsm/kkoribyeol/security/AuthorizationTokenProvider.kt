package com.dsm.kkoribyeol.security

import com.dsm.kkoribyeol.exception.InvalidTokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*

class AuthorizationTokenProvider(
    secretKey: String,
) {
    private val base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())

    fun createAuthorizationToken(userId: String, millisecondOfExpirationTime: Long) =
        AuthorizationToken.newAuthorizationToken(
            data = userId,
            millisecondOfExpirationTime = millisecondOfExpirationTime,
            secretKey = base64EncodedSecretKey,
        )

    fun toAuthorizationToken(token: String) =
        AuthorizationToken.newAuthorizationToken(
            token = token,
            secretKey = base64EncodedSecretKey,
        )

    fun getAuthentication(authorizationToken: AuthorizationToken) =
        if (authorizationToken.validate())
            UsernamePasswordAuthenticationToken(
                User(
                    authorizationToken.getData(),
                    "",
                    listOf(SimpleGrantedAuthority(authorizationToken.getData())),
                ),
                authorizationToken,
            )
        else
            throw InvalidTokenException()
}