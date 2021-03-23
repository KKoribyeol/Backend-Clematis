package com.dsm.clematis.domain.project.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.project.exception.ProjectNotFoundException
import com.dsm.clematis.domain.project.repository.ProjectRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull


internal class ProjectModificationServiceTest {
    private val projectRepository = mockk<ProjectRepository>()
    private val testService = ProjectModificationService(
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
    fun `프로젝트 내용 수정하기`() {
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        testService.modifyProject(
            projectCode = "savedProject-finally",
            newProjectName = "newProject",
            newProjectDescription = "newDescription",
        )

        verify(exactly = 1) { projectRepository.findByIdOrNull(savedProject.code) }
    }

    @Test
    fun `프로젝트 내용 수정하기 - throw ProjectNotFoundException`() {
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        assertThrows<ProjectNotFoundException> {
            testService.modifyProject(
                projectCode = "nonExistProject-finally",
                newProjectName = "newProject",
                newProjectDescription = "newDescription",
            )
        }

        verify(exactly = 1) { projectRepository.findByIdOrNull(nonExistProject.code) }
    }
}