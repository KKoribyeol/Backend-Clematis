package com.dsm.kkoribyeol.service.provider

import com.dsm.kkoribyeol.service.attribute.Token
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