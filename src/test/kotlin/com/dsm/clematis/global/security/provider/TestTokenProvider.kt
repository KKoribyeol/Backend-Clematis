package com.dsm.clematis.global.security.provider

import com.dsm.clematis.global.attribute.Token
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
        validateToken(token, Token.ACCESS)
        return "savedIdId"
    }

    override fun validateToken(token: String, tokenType: Token) =
        token == "this-is-test-token"
                || token == "this-is-access-token"
                || token == "this-is-refresh-token"

    override fun isToken(token: String, tokenType: Token) =
        token == "this-is-test-token"
}