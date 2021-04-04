package com.dsm.clematis.global.security.provider

import com.dsm.clematis.global.attribute.Token
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenProvider(
    @Value("\${TOKEN_SECRET_KEY:spring-security-love}")
    private val secretKey: String,
    private val userDetailsService: UserDetailsService,
) {
    private val encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())

    fun createToken(accountId: String, tokenType: Token): String =
        Jwts.builder()
            .setSubject(accountId)
            .setExpiration(Date(System.currentTimeMillis() + tokenType.millisecondOfExpirationTime))
            .signWith(SignatureAlgorithm.HS384, encodedSecretKey)
            .compact()

    fun getData(token: String): String =
        Jwts.parser()
            .setSigningKey(encodedSecretKey)
            .parseClaimsJws(token)
            .body
            .subject

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getData(token))
        return UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities,
        )
    }

    fun extractToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")

    fun validateToken(token: String) =
        try {
            val expirationTime = Jwts.parser()
                .setSigningKey(encodedSecretKey)
                .parseClaimsJws(token)
                .body
                .expiration
            expirationTime.after(Date())
        } catch (e: Exception) { false }
}