package com.dsm.clematis.domain.project.service.provider

interface ProjectCodeProvider {
    fun generateRandomCode(): String
}