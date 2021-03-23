package com.dsm.kkoribyeol.domain.project.service.provider

import com.dsm.kkoribyeol.global.attribute.Token
import com.dsm.kkoribyeol.global.security.provider.AuthenticationProvider
import com.dsm.kkoribyeol.global.security.provider.TokenProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class TestTokenProvider(
    authenticationProvider: AuthenticationProvider,
) : TokenProvider("spring-security-love", authenticationProvider) {

    override fun createToken(accountId: String, tokenType: Token) =
        "this-is-test-token"

    override fun getData(token: String): String {
        validateToken(token)
        return "savedIdId"
    }

    override fun validateToken(token: String) =
        token == "this-is-test-token"
}