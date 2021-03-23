package com.dsm.kkoribyeol.domain.account.controller.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)