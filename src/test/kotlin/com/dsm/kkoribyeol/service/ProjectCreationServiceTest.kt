package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.domain.Project
import com.dsm.kkoribyeol.exception.AccountNotFoundException
import com.dsm.kkoribyeol.exception.AlreadyExistProjectException
import com.dsm.kkoribyeol.repository.AccountRepository
import com.dsm.kkoribyeol.repository.ProjectRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class ProjectCreationServiceTest {
    private val projectRepository = mockk<ProjectRepository>()
    private val accountRepository = mockk<AccountRepository>()
    private val testService = ProjectCreationService(
        projectRepository = projectRepository,
        accountRepository = accountRepository,
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )
    private val nonExistAccount = Account(
        id = "idIdIdId",
        password = "password",
        name = "nameName",
    )
    private val savedProject = Project(
        id = "savedProject-finally",
        name = "savedProject",
        description = "savedDescription",
        owner = savedAccount,
    )
    private val nonExistProject = Project(
        id = "project-finally",
        name = "project",
        description = "description",
        owner = savedAccount,
    )

    @Test
    fun `프로젝트 생성하기`() {
        every { projectRepository.existsByName(savedProject.name) } returns true
        every { projectRepository.existsByName(nonExistProject.name) } returns false
        every { projectRepository.save(any()) } returns nonExistProject
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null

        val projectId = testService.createProject(
            accountId = "savedIdId",
            projectName = "project",
            projectDescription = "description",
        )

        assertThat(projectId).isEqualTo(nonExistProject.id)

        verify(exactly = 1) { projectRepository.existsByName(nonExistProject.name) }
        verify(exactly = 1) { projectRepository.save(any()) }
        verify(exactly = 1) { accountRepository.findByIdOrNull(savedAccount.id) }
    }

    @Test
    fun `프로젝트 생성하기 - throw AlreadyExistProjectException`() {
        every { projectRepository.existsByName(savedProject.name) } returns true
        every { projectRepository.existsByName(nonExistProject.name) } returns false
        every { projectRepository.save(any()) } returns nonExistProject
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null

        assertThrows<AlreadyExistProjectException> {
            testService.createProject(
                accountId = "savedIdId",
                projectName = "savedProject",
                projectDescription = "savedDescription",
            )
        }

        verify(exactly = 1) { projectRepository.existsByName(savedProject.name) }
        verify(exactly = 0) { projectRepository.save(any()) }
        verify(exactly = 0) { accountRepository.findByIdOrNull(any()) }
    }

    @Test
    fun `프로젝트 생성하기 - throw AccountNotFoundException`() {
        every { projectRepository.existsByName(savedProject.name) } returns true
        every { projectRepository.existsByName(nonExistProject.name) } returns false
        every { projectRepository.save(any()) } returns nonExistProject
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null

        assertThrows<AccountNotFoundException> {
            testService.createProject(
                accountId = "idIdIdId",
                projectName = "project",
                projectDescription = "description",
            )
        }

        verify(exactly = 1) { projectRepository.existsByName(nonExistProject.name) }
        verify(exactly = 0) { projectRepository.save(any()) }
        verify(exactly = 1) { accountRepository.findByIdOrNull(nonExistAccount.id) }
    }
}