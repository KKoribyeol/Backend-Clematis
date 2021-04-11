package com.dsm.clematis.domain.authentication.controller

import com.dsm.clematis.domain.authentication.controller.request.LoginRequest
import com.dsm.clematis.domain.authentication.controller.response.LoginResponse
import com.dsm.clematis.domain.account.service.AccountValidationService
import com.dsm.clematis.domain.authentication.controller.request.LogoutRequest
import com.dsm.clematis.domain.authentication.controller.request.ReissueTokenRequest
import com.dsm.clematis.domain.authentication.controller.response.TokenResponse
import com.dsm.clematis.domain.authentication.service.AuthenticationCreationService
import com.dsm.clematis.domain.authentication.service.AuthenticationDeletionService
import com.dsm.clematis.domain.authentication.service.AuthenticationValidationService
import com.dsm.clematis.global.attribute.Token
import com.dsm.clematis.global.security.provider.TokenProvider
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val accountValidationService: AccountValidationService,
    private val authenticationCreationService: AuthenticationCreationService,
    private val authenticationDeletionService: AuthenticationDeletionService,
    private val authenticationValidationService: AuthenticationValidationService,
    private val tokenProvider: TokenProvider,
) {

    @PostMapping
    fun login(
        @Valid
        @RequestBody
        request: LoginRequest
    ): LoginResponse {
        accountValidationService.validateAccount(
            accountId = request.accountId,
            accountPassword = request.accountPassword,
        )

        return LoginResponse(
            accessToken = authenticationCreationService.createAccessToken(request.accountId),
            refreshToken = authenticationCreationService.createRefreshToken(request.accountId),
        )
    }

    @DeleteMapping
    fun logout(
        @Valid
        @RequestBody
        request: LogoutRequest,
    ) {
        authenticationValidationService.validateToken(
            token = request.token,
            tokenType = Token.REFRESH,
        )

        authenticationDeletionService.deleteToken(
            accountId = tokenProvider.getData(request.token),
        )
    }

    @PostMapping("/token")
    fun reissueAccessToken(
        @Valid
        @RequestBody
        request: ReissueTokenRequest,
    ): TokenResponse {
        authenticationValidationService.validateBothToken(
            accessToken = request.accessToken,
            refreshToken = request.refreshToken,
        )

        return TokenResponse(
            token = authenticationCreationService.createAccessToken(
                accountId = tokenProvider.getData(request.accessToken),
            )
        )
    }
}