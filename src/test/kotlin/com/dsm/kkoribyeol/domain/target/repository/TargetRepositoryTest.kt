package com.dsm.kkoribyeol.domain.target.repository

import com.dsm.kkoribyeol.domain.target.repository.TargetRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class TargetRepositoryTest(
    private val targetRepository: TargetRepository,
) {

    @Test
    fun `findByProjectCodeAndToken`() {
        val findTarget = targetRepository.findByProjectCodeAndToken(
            code = "savedProject-finally",
            token = "savedToken",
        )

        assertThat(findTarget?.id).isEqualTo(1)
        assertThat(findTarget?.token).isEqualTo("savedToken")
        assertThat(findTarget?.project?.code).isEqualTo("savedProject-finally")
        assertThat(findTarget?.nickname).isEqualTo("savedNickname")
        assertThat(findTarget?.name).isEqualTo("savedName")
    }

    @Test
    fun `findByProjectCodeAndToken - 존재하지 않음`() {
        val findTarget = targetRepository.findByProjectCodeAndToken(
            code = "savedProject-finally",
            token = "nonExistToken",
        )

        assertThat(findTarget).isNull()
    }

    @Test
    fun `deleteByProjectCodeAndTokenIn`() {
        targetRepository.deleteByProjectCodeAndTokenIn(
            code = "savedProject-finally",
            tokens = listOf("savedToken"),
        )
    }

    @Test
    fun `existsByProjectCodeAndTokenIn - 존재함`() {
        val isExist = targetRepository.existsByProjectCodeAndTokenIn(
            code = "savedProject-finally",
            tokens = listOf("savedToken"),
        )

        assertThat(isExist).isTrue
    }

    @Test
    fun `existsByProjectCodeAndTokenIn - 존재하지 않음`() {
        val isExist = targetRepository.existsByProjectCodeAndTokenIn(
            code = "savedProject-finally",
            tokens = listOf("nonExistToken"),
        )

        assertThat(isExist).isFalse
    }

    @Test
    fun `existsByProjectCodeAndNicknameIn - 존재함`() {
        val isExist = targetRepository.existsByProjectCodeAndNicknameIn(
            code = "savedProject-finally",
            nicknames = listOf("savedNickname"),
        )

        assertThat(isExist).isTrue
    }

    @Test
    fun `existsByProjectCodeAndNicknameIn - 존재하지 않음`() {
        val isExist = targetRepository.existsByProjectCodeAndNicknameIn(
            code = "savedProject-finally",
            nicknames = listOf("nonExistNickname"),
        )

        assertThat(isExist).isFalse
    }
}