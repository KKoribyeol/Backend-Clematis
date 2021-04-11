package com.dsm.clematis.domain.authentication.controller.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)