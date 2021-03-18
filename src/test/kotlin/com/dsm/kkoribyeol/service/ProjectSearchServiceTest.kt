package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.domain.Project
import com.dsm.kkoribyeol.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.repository.ProjectRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class ProjectSearchServiceTest {
    private val projectRepository = mockk<ProjectRepository>()
    private val testService = ProjectSearchService(
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
        owner = savedAccount
    )

    @Test
    fun `프로젝트 단일 조회하기`() {
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null
        every { projectRepository.findAll() } returns listOf(savedProject)

        val project = testService.searchProject(projectCode = "savedProject-finally")

        assertThat(project).isEqualTo(savedProject)

        verify(exactly = 1) { projectRepository.findByIdOrNull(savedProject.code) }
        verify(exactly = 0) { projectRepository.findAll() }
    }

    @Test
    fun `프로젝트 단일 조회하기 - throw ProjectNotFoundException`() {
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null
        every { projectRepository.findAll() } returns listOf(savedProject)

        assertThrows<ProjectNotFoundException> {
            testService.searchProject(projectCode = "nonExistProject-finally")
        }

        verify(exactly = 1) { projectRepository.findByIdOrNull(nonExistProject.code) }
        verify(exactly = 0) { projectRepository.findAll() }
    }

    @Test
    fun `프로젝트 전체 조회하기`() {
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null
        every { projectRepository.findAll() } returns listOf(savedProject)

        val projects = testService.searchAllProject()

        assertThat(projects).isEqualTo(listOf(savedProject))

        verify(exactly = 0) { projectRepository.findByIdOrNull(any()) }
        verify(exactly = 1) { projectRepository.findAll() }
    }
}