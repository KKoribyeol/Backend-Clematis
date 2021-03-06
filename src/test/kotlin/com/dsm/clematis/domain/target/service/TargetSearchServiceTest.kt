package com.dsm.clematis.domain.target.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.target.domain.Target
import com.dsm.clematis.domain.target.exception.TargetNotFoundException
import com.dsm.clematis.domain.target.repository.TargetRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TargetSearchServiceTest {
    private val targetRepository = mockk<TargetRepository>()
    private val testService = TargetSearchService(
        targetRepository = targetRepository,
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )
    private val savedProject = Project(
        code = "savedProject-finally",
        name = "savedProject",
        description = "savedDescription",
        owner = savedAccount,
    )
    private val savedTarget = Target(
        token = "savedToken",
        nickname = "savedNickname",
        name = "savedName",
        project = savedProject,
    )
    private val nonExistTarget = Target(
        token = "nonExistToken",
        nickname = "nonExistNickname",
        name = "nonExistName",
        project = savedProject,
    )

    @Test
    fun `타겟 단일 조회하기`() {
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, savedTarget.token) } returns savedTarget
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, nonExistTarget.token) } returns null

        val findTarget = testService.searchTarget(
            projectCode = "savedProject-finally",
            targetToken = "savedToken",
        )

        assertThat(findTarget.token).isEqualTo("savedToken")
        assertThat(findTarget.nickname).isEqualTo("savedNickname")
        assertThat(findTarget.name).isEqualTo("savedName")
        assertThat(findTarget.project).isEqualTo(savedProject)

        verify(exactly = 1) { targetRepository.findByProjectCodeAndToken(savedProject.code, savedTarget.token) }
    }

    @Test
    fun `타겟 단일 조회하기 - TargetNotFoundException`() {
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, savedTarget.token) } returns savedTarget
        every { targetRepository.findByProjectCodeAndToken(any(), any()) } returns null
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, nonExistTarget.token) } returns null

        assertThrows<TargetNotFoundException> {
            testService.searchTarget(
                projectCode = "savedProject-finally",
                targetToken = "nonExistToken",
            )
        }

        verify(exactly = 1) { targetRepository.findByProjectCodeAndToken(savedProject.code, nonExistTarget.token) }
    }

    @Test
    fun `타겟 다중 조회하기`() {
        every { targetRepository.findByProjectCode(savedProject.code) } returns listOf(savedTarget)

        val findTargetAll = testService.searchAllTarget(savedProject.code)

        assertThat(findTargetAll).isEqualTo(listOf(savedTarget))

        verify(exactly = 1) { targetRepository.findByProjectCode(savedProject.code) }
    }
}