package com.dsm.kkoribyeol.domain.project.service.provider

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component

private const val RANDOM_CODE_LENGTH = 7
private const val IS_INCLUDE_LETTER = true
private const val IS_INCLUDE_NUMBER = true

@Component
class RandomProjectCodeProvider : ProjectCodeProvider {

    override fun generateRandomCode(): String =
        RandomStringUtils.random(RANDOM_CODE_LENGTH, IS_INCLUDE_LETTER, IS_INCLUDE_NUMBER)
}