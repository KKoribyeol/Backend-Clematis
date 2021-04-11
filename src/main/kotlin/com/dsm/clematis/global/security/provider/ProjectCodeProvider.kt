package com.dsm.clematis.global.security.provider

interface ProjectCodeProvider {
    fun generateRandomCode(): String
}