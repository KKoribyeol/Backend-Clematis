package com.dsm.kkoribyeol.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

class AuthorizationToken(
    private val token: String,
    private val secretKey: String,
) {

    fun getData(): String =
        Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .get("userId", String::class.java)

    fun validate() =
        try {
            val currentTime = Date()
            val expirationTime = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
                .expiration
            expirationTime.after(currentTime)
        } catch (e: Exception) { false }

    companion object {
        fun newAuthorizationToken(data: String, millisecondOfExpirationTime: Long, secretKey: String) =
            AuthorizationToken(
                secretKey = secretKey,
                token = Jwts.builder()
                    .claim("userId", data)
                    .signWith(SignatureAlgorithm.HS384, secretKey)
                    .setExpiration(Date(System.currentTimeMillis() + millisecondOfExpirationTime))
                    .compact(),
            )

        fun newAuthorizationToken(token: String, secretKey: String) =
            AuthorizationToken(
                secretKey = secretKey,
                token = token,
            )
    }
}