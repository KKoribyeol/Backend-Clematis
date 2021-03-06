package com.dsm.clematis.domain.target.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.target.domain.Target
import com.dsm.clematis.domain.target.exception.TargetNotFoundException
import com.dsm.clematis.domain.target.repository.TargetRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TargetModificationServiceTest {
    private val targetRepository = mockk<TargetRepository>()
    private val testService = TargetModificationService(
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
    fun `타겟 정보 변경하기`() {
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, savedTarget.token) } returns savedTarget
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, nonExistTarget.token) } returns null

        testService.modifyTarget(
            projectCode = "savedProject-finally",
            targetToken = "savedToken",
            newNickname = null,
            newName = null,
        )

        verify(exactly = 1) { targetRepository.findByProjectCodeAndToken(savedProject.code, savedTarget.token) }
    }

    @Test
    fun `타겟 정보 변경하기 - TargetNotFoundException`() {
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, savedTarget.token) } returns savedTarget
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, nonExistTarget.token) } returns null

        assertThrows<TargetNotFoundException> {
            testService.modifyTarget(
                projectCode = "savedProject-finally",
                targetToken = "nonExistToken",
                newNickname = null,
                newName = null,
            )
        }

        verify(exactly = 1) { targetRepository.findByProjectCodeAndToken(savedProject.code, nonExistTarget.token) }
    }
}