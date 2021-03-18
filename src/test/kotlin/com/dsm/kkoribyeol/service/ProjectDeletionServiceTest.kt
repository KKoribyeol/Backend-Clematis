package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.domain.Project
import com.dsm.kkoribyeol.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.repository.ProjectRepository
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ProjectDeletionServiceTest {
    private val projectRepository = mockk<ProjectRepository>()
    private val testService = ProjectDeletionService(
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

    @Test
    fun `프로젝트 삭제하기`() {
        every { projectRepository.existsById(savedProject.code) } returns true
        every { projectRepository.existsById(nonExistProject.code) } returns false
        every { projectRepository.deleteById(savedProject.code) } just Runs

        testService.deleteProject(
            projectCode = "savedProject-finally",
        )

        verify(exactly = 1) { projectRepository.existsById(savedProject.code) }
        verify(exactly = 1) { projectRepository.deleteById(savedProject.code) }
    }

    @Test
    fun `프로젝트 삭제하기 - throw ProjectNotFoundException`() {
        every { projectRepository.existsById(savedProject.code) } returns true
        every { projectRepository.existsById(nonExistProject.code) } returns false
        every { projectRepository.deleteById(savedProject.code) } just Runs

        assertThrows<ProjectNotFoundException> {
            testService.deleteProject(
                projectCode = "nonExistProject-finally",
            )
        }

        verify(exactly = 1) { projectRepository.existsById(nonExistProject.code) }
        verify(exactly = 0) { projectRepository.deleteById(any()) }
    }
}