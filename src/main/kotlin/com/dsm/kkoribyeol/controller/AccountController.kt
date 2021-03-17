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

    private val accountRepository: AccountRepository,
) {

    @PostMapping
    fun join(
        @RequestBody @Valid
        request: JoinRequest
    ) = authenticationCreationService.createAccount(
        accountId = request.accountId,
        accountPassword = request.accountPassword,
        accountName = request.accountName,
    )

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
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
        @RequestBody @Valid
        request: PasswordModificationRequest,
    ) = accountModificationService.modifyPassword(
        accountId = authenticationProvider.getAccountIdByAuthentication(),
        accountPassword = request.newPassword,
        accountConfirmPassword = request.confirmNewPassword,
    )

    @PatchMapping("/name")
    fun modifyName(
        @RequestBody @Valid
        request: NameModificationRequest,
    ) = accountModificationService.modifyName(
        accountId = authenticationProvider.getAccountIdByAuthentication(),
        accountName = request.newName,
    )

    @DeleteMapping
    fun accountWithdrawal() =
        accountDeletionService.deleteAccount(
            accountId = authenticationProvider.getAccountIdByAuthentication(),
        )
}