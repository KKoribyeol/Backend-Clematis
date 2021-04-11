package com.dsm.clematis.domain.project.service.provider

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomProjectCodeProvider : ProjectCodeProvider {
    private val codeCharacterPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun generateRandomCode() =
        (1..7)
            .map { Random.nextInt(0, codeCharacterPool.size) }
            .map { codeCharacterPool[it] }
            .joinToString("")
}