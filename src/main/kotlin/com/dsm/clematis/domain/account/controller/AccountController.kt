package com.dsm.clematis.domain.account.controller

import com.dsm.clematis.domain.account.controller.request.JoinRequest
import com.dsm.clematis.domain.account.controller.request.LoginRequest
import com.dsm.clematis.domain.account.controller.request.AccountNameModificationRequest
import com.dsm.clematis.domain.account.controller.request.AccountPasswordModificationRequest
import com.dsm.clematis.domain.account.controller.response.AccountNameResponse
import com.dsm.clematis.domain.account.controller.response.LoginResponse
import com.dsm.clematis.domain.account.service.AccountDeletionService
import com.dsm.clematis.domain.account.service.AccountModificationService
import com.dsm.clematis.domain.account.service.AccountCreationService
//import com.dsm.clematis.global.security.provider.AuthenticationProvider
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountCreationService: AccountCreationService,
    private val accountModificationService: AccountModificationService,
    private val accountDeletionService: AccountDeletionService,
//    private val authenticationProvider: AuthenticationProvider,
) {

    @PostMapping
    fun join(
        @RequestBody @Valid
        request: JoinRequest
    ) = accountCreationService.createAccount(
        accountId = request.accountId,
        accountPassword = request.accountPassword,
        accountName = request.accountName,
    )

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
        request: LoginRequest
    ): LoginResponse {
        accountCreationService.validateAccount(
            accountId = request.accountId,
            accountPassword = request.accountPassword,
        )

        return LoginResponse(
            accessToken = accountCreationService.createAccessToken(request.accountId),
            refreshToken = accountCreationService.createRefreshToken(request.accountId),
        )
    }

    @PatchMapping("/password")
    fun modifyPassword(
        @RequestBody @Valid
        request: AccountPasswordModificationRequest,
    ) = accountModificationService.modifyAccountPassword(
//        accountId = authenticationProvider.getAccountIdByAuthentication(),
        accountId = "ad",
        accountPassword = request.newPassword,
        accountConfirmPassword = request.confirmNewPassword,
    )

    @PatchMapping("/name")
    fun modifyName(
        @RequestBody @Valid
        request: AccountNameModificationRequest,
    ) = accountModificationService.modifyAccountName(
//        accountId = authenticationProvider.getAccountIdByAuthentication(),
        accountId = "ad",
        accountName = request.newName,
    )

    @DeleteMapping
    fun accountWithdrawal() =
        accountDeletionService.deleteAccount(
//            accountId = authenticationProvider.getAccountIdByAuthentication(),
            accountId = "ad",
        )

    @GetMapping("/name")
    fun getAccountName() =
        AccountNameResponse(
//            name = authenticationProvider.getAccountName()
            name = "ad",
        )
}