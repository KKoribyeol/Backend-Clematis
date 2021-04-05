package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.project.repository.ProjectRepository
import com.dsm.clematis.domain.template.domain.Template
import com.dsm.clematis.domain.template.exception.AlreadyExistTemplateException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class TemplateCreationServiceTest {
    private val templateRepository = mockk<TemplateRepository>()
    private val projectRepository = mockk<ProjectRepository>()
    private val testService = TemplateCreationService(templateRepository, projectRepository)

    private val savedAccount = Account("savedIdId", "savedPassword", "savedName")
    private val savedProject = Project("savedProject-finally", "savedProject", "savedDescription", savedAccount)
    private val savedTemplate = Template("saved title", "saved body", savedProject)
    private val newTemplate = Template("title", "body", savedProject)

    @BeforeEach
    fun setup() {
        savedTemplate.id = 1
        newTemplate.id = 2
    }

    @Test
    fun `푸시 템플릿 생성하기`() {
        every { templateRepository.existsByTitleAndBodyAndProjectCode(savedTemplate.title, savedTemplate.body, "savedProject-finally") } returns true
        every { templateRepository.existsByTitleAndBodyAndProjectCode(newTemplate.title, newTemplate.body, "savedProject-finally") } returns false
        every { templateRepository.save(any()) } returns newTemplate
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject

        val creationNumber = testService.createTemplate("title", "body", "savedProject-finally")

        assertThat(creationNumber).isEqualTo(2)
        verify(exactly = 1) { templateRepository.existsByTitleAndBodyAndProjectCode(newTemplate.title, newTemplate.body, "savedProject-finally") }
        verify(exactly = 1) { templateRepository.save(any()) }
    }

    @Test
    fun `푸시 템플릿 생성하기 - throw AlreadyExistTemplateException`() {
        every { templateRepository.existsByTitleAndBodyAndProjectCode(savedTemplate.title, savedTemplate.body, "savedProject-finally") } returns true
        every { templateRepository.existsByTitleAndBodyAndProjectCode(newTemplate.title, newTemplate.body, "savedProject-finally") } returns false
        every { templateRepository.save(any()) } returns newTemplate
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject

        assertThrows<AlreadyExistTemplateException> { testService.createTemplate("saved title", "saved body", "savedProject-finally") }

        verify(exactly = 1) { templateRepository.existsByTitleAndBodyAndProjectCode(savedTemplate.title, savedTemplate.body, "savedProject-finally") }
        verify(exactly = 0) { templateRepository.save(any()) }
    }
}