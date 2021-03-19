package com.dsm.kkoribyeol.service.provider

import org.springframework.stereotype.Component
import kotlin.random.Random

private const val PROJECT_CODE_LENGTH = 7

@Component
class RandomProjectCodeProvider : ProjectCodeProvider {
    private val codeCharacterPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun generateRandomCode(): String =
        (1..PROJECT_CODE_LENGTH)
            .map { Random.nextInt(0, codeCharacterPool.size) }
            .map { codeCharacterPool[it] }
            .joinToString("")
}