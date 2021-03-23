package com.dsm.kkoribyeol.domain.target.service

import com.dsm.kkoribyeol.domain.target.controller.request.TargetRegistrationAllRequest.TargetRegistrationRequest
import com.dsm.kkoribyeol.domain.account.domain.Account
import com.dsm.kkoribyeol.domain.project.domain.Project
import com.dsm.kkoribyeol.domain.target.domain.Target
import com.dsm.kkoribyeol.domain.target.exception.AlreadyExistTargetException
import com.dsm.kkoribyeol.domain.project.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.domain.project.repository.ProjectRepository
import com.dsm.kkoribyeol.domain.target.repository.TargetRepository
import com.dsm.kkoribyeol.domain.target.service.TargetRegistrationService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class TargetRegistrationServiceTest {
    private val targetRepository = mockk<TargetRepository>()
    private val projectRepository = mockk<ProjectRepository>()
    private val testService = TargetRegistrationService(
        targetRepository = targetRepository,
        projectRepository = projectRepository,
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
    private val nonExistProject = Project(
        code = "nonExistProject-finally",
        name = "nonExistProject",
        description = "nonExistDescription",
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
    fun `타겟 등록하기`() {
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(savedTarget.token)) } returns true
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(nonExistTarget.token)) } returns false
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(savedTarget.nickname)) } returns true
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(nonExistTarget.nickname)) } returns false
        every { targetRepository.saveAll(listOf(nonExistTarget)) } returns listOf(nonExistTarget)
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedTarget.project
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        testService.registerTarget(
            projectCode = "savedProject-finally",
            targets = listOf(
                TargetRegistrationRequest(
                    targetToken = "nonExistToken",
                    targetNickname = "nonExistNickname",
                    targetName = "nonExistName",
                ),
            ),
        )

        verify(exactly = 1) { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(nonExistTarget.token)) }
        verify(exactly = 1) { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(nonExistTarget.nickname)) }
        verify(exactly = 1) { targetRepository.saveAll(listOf(nonExistTarget)) }
        verify(exactly = 1) { projectRepository.findByIdOrNull(savedProject.code) }
    }

    @Test
    fun `타겟 등록하기 - AlreadyExistTargetException - {토큰 중복}`() {
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(savedTarget.token)) } returns true
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(nonExistTarget.token)) } returns false
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(savedTarget.nickname)) } returns true
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(nonExistTarget.nickname)) } returns false
        every { targetRepository.saveAll(listOf(nonExistTarget)) } returns listOf(nonExistTarget)
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedTarget.project
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        assertThrows<AlreadyExistTargetException> {
            testService.registerTarget(
                projectCode = "savedProject-finally",
                targets = listOf(
                    TargetRegistrationRequest(
                        targetToken = "savedToken",
                        targetNickname = "nonExistNickname",
                        targetName = "nonExistName",
                    ),
                ),
            )
        }

        verify(exactly = 1) { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(savedTarget.token)) }
        verify(exactly = 0) { targetRepository.existsByProjectCodeAndNicknameIn(any(), listOf(nonExistTarget.nickname)) }
        verify(exactly = 0) { targetRepository.existsByProjectCodeAndNicknameIn(any(), listOf(savedTarget.nickname)) }
        verify(exactly = 0) { targetRepository.saveAll(listOf(nonExistTarget)) }
        verify(exactly = 0) { projectRepository.findByIdOrNull(any()) }
    }

    @Test
    fun `타겟 등록하기 - AlreadyExistTargetException - {닉네임 중복}`() {
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(savedTarget.token)) } returns true
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(nonExistTarget.token)) } returns false
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(savedTarget.nickname)) } returns true
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(nonExistTarget.nickname)) } returns false
        every { targetRepository.saveAll(listOf(nonExistTarget)) } returns listOf(nonExistTarget)
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedTarget.project
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        assertThrows<AlreadyExistTargetException> {
            testService.registerTarget(
                projectCode = "savedProject-finally",
                targets = listOf(
                    TargetRegistrationRequest(
                        targetToken = "nonExistToken",
                        targetNickname = "savedNickname",
                        targetName = "nonExistName",
                    ),
                ),
            )
        }

        verify(exactly = 1) { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(nonExistTarget.token)) }
        verify(exactly = 1) { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(savedTarget.nickname)) }
        verify(exactly = 0) { targetRepository.saveAll(listOf(nonExistTarget)) }
        verify(exactly = 0) { projectRepository.findByIdOrNull(any()) }
    }

    @Test
    fun `타겟 등록하기 - ProjectNotFoundException`() {
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(savedTarget.token)) } returns true
        every { targetRepository.existsByProjectCodeAndTokenIn(savedTarget.project.code, listOf(nonExistTarget.token)) } returns false
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(savedTarget.nickname)) } returns true
        every { targetRepository.existsByProjectCodeAndNicknameIn(savedTarget.project.code, listOf(nonExistTarget.nickname)) } returns false
        every { targetRepository.existsByProjectCodeAndTokenIn(nonExistProject.code, listOf(nonExistTarget.token)) } returns false
        every { targetRepository.existsByProjectCodeAndNicknameIn(nonExistProject.code, listOf(nonExistTarget.nickname)) } returns false
        every { targetRepository.saveAll(listOf(nonExistTarget)) } returns listOf(nonExistTarget)
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedTarget.project
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        assertThrows<ProjectNotFoundException> {
            testService.registerTarget(
                projectCode = "nonExistProject-finally",
                targets = listOf(
                    TargetRegistrationRequest(
                        targetToken = "nonExistToken",
                        targetNickname = "nonExistNickname",
                        targetName = "nonExistName",
                    ),
                ),
            )
        }

        verify(exactly = 1) { targetRepository.existsByProjectCodeAndTokenIn(nonExistProject.code, listOf(nonExistTarget.token)) }
        verify(exactly = 1) { targetRepository.existsByProjectCodeAndNicknameIn(nonExistProject.code, listOf(nonExistTarget.nickname)) }
        verify(exactly = 0) { targetRepository.saveAll(listOf(nonExistTarget)) }
        verify(exactly = 1) { projectRepository.findByIdOrNull(nonExistProject.code) }
    }

    @Test
    fun `타겟 등록 해제하기`() {
        every { targetRepository.deleteByProjectCodeAndTokenIn(savedProject.code, listOf(savedTarget.token)) } just Runs

        testService.unregisterTarget(
            projectCode = "savedProject-finally",
            tokens = listOf("savedToken"),
        )

        verify(exactly = 1) { targetRepository.deleteByProjectCodeAndTokenIn(savedProject.code, listOf(savedTarget.token)) }
    }
}