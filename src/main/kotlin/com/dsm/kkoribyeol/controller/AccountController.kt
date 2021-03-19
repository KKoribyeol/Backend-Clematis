package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.JoinRequest
import com.dsm.kkoribyeol.controller.request.LoginRequest
import com.dsm.kkoribyeol.controller.request.NameModificationRequest
import com.dsm.kkoribyeol.controller.request.PasswordModificationRequest
import com.dsm.kkoribyeol.controller.response.LoginResponse
import com.dsm.kkoribyeol.repository.AccountRepository
import com.dsm.kkoribyeol.service.AccountDeletionService
import com.dsm.kkoribyeol.service.AccountModificationService
import com.dsm.kkoribyeol.service.AuthenticationCreationService
import com.dsm.kkoribyeol.service.provider.AuthenticationProvider
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/account")
class AccountController(
    private val authenticationCreationService: AuthenticationCreationService,
    private val accountModificationService: AccountModificationService,
    private val accountDeletionService: AccountDeletionService,
    private val authenticationProvider: AuthenticationProvider,
) {

    @PostMapping
    fun join(
        @Valid
        @RequestBody
        request: JoinRequest
    ) = authenticationCreationService.createAccount(
        accountId = request.accountId,
        accountPassword = request.accountPassword,
        accountName = request.accountName,
    )

    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody
        request: LoginRequest
    ): LoginResponse {
        authenticationCreationService.validateAccount(
            accountId = request.accountId,
            accountPassword = request.accountPassword,
        )

        return LoginResponse(
            accessToken = authenticationCreationService.createAccessToken(request.accountId),
            refreshToken = authenticationCreationService.createRefreshToken(request.accountId),
        )
    }

    @PatchMapping("/password")
    fun modifyPassword(
        @Valid
        @RequestBody
        request: PasswordModificationRequest,
    ) = accountModificationService.modifyAccountPassword(
        accountId = authenticationProvider.getAccountIdByAuthentication(),
        accountPassword = request.newPassword,
        accountConfirmPassword = request.confirmNewPassword,
    )

    @PatchMapping("/name")
    fun modifyName(
        @Valid
        @RequestBody
        request: NameModificationRequest,
    ) = accountModificationService.modifyAccountName(
        accountId = authenticationProvider.getAccountIdByAuthentication(),
        accountName = request.newName,
    )

    @DeleteMapping
    fun accountWithdrawal() =
        accountDeletionService.deleteAccount(
            accountId = authenticationProvider.getAccountIdByAuthentication(),
        )
}