package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.JoinRequest
import com.dsm.kkoribyeol.controller.request.LoginRequest
import com.dsm.kkoribyeol.controller.request.PasswordModificationRequest
import com.dsm.kkoribyeol.controller.response.LoginResponse
import com.dsm.kkoribyeol.service.AccountModificationService
import com.dsm.kkoribyeol.service.AuthenticationService
import com.dsm.kkoribyeol.service.provider.AuthenticationProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AccountController(
    private val authenticationService: AuthenticationService,
    private val accountModificationService: AccountModificationService,
    private val authenticationProvider: AuthenticationProvider,
) {

    @PostMapping("/join")
    fun join(@RequestBody @Valid request: JoinRequest) =
        authenticationService.createAccount(
            accountId = request.accountId,
            accountPassword = request.accountPassword,
            accountName = request.accountName,
        )

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): LoginResponse {
        authenticationService.validateAccount(
            accountId = request.accountId,
            accountPassword = request.accountPassword,
        )

        return LoginResponse(
            accessToken = authenticationService.createAccessToken(request.accountId),
            refreshToken = authenticationService.createRefreshToken(request.accountId),
        )
    }

    @PatchMapping("/password")
    fun modifyPassword(
        @RequestBody @Valid request: PasswordModificationRequest,
    ) = accountModificationService.modifyPassword(
            accountId = authenticationProvider.getAccountIdByAuthentication(),
            accountPassword = request.newPassword,
            accountConfirmPassword = request.confirmNewPassword,
        )
}