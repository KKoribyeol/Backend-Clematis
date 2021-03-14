package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.JoinRequest
import com.dsm.kkoribyeol.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AccountController(
    private val authenticationService: AuthenticationService,
) {

    @PostMapping("/join")
    fun join(@RequestBody @Valid request: JoinRequest) =
        authenticationService.createAccount(
            accountId = request.accountId,
            accountPassword = request.accountPassword,
            accountName = request.accountName,
        )

    @PostMapping("/login")
    fun login() {

    }
}