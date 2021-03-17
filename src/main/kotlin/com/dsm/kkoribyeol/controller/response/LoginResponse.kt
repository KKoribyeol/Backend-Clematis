package com.dsm.kkoribyeol.controller.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)