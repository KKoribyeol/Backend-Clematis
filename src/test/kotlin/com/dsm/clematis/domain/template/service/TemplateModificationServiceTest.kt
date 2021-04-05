package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.template.domain.Template
import com.dsm.clematis.domain.template.exception.TemplateNotFoundException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.anyLong
import org.springframework.data.repository.findByIdOrNull

class TemplateModificationServiceTest {
    private val templateRepository = mockk<TemplateRepository>()
    private val testService = TemplateModificationService(templateRepository)

    private val savedAccount = Account("savedIdId", "savedPassword", "savedName")
    private val savedProject = Project("savedProject-finally", "savedProject", "savedDescription", savedAccount)
    private val savedTemplate = Template("saved title", "saved body", savedProject)

    @BeforeEach
    fun setup() {
        savedTemplate.id = 1
    }

    @Test
    fun `푸시 템플릿 수정하기`() {
        every { templateRepository.findByIdAndProjectCode(1, savedProject.code) } returns savedTemplate
        every { templateRepository.findByIdAndProjectCode(anyLong(), any()) } returns null

        testService.modifyTemplate(1, "modified title", "modified body", "savedProject-finally")

        verify(exactly = 1) { templateRepository.findByIdAndProjectCode(1, savedProject.code) }
    }

    @Test
    fun `푸시 템플릿 수정하기 - throw TemplateNotFoundException`() {
        every { templateRepository.findByIdAndProjectCode(1, savedProject.code) } returns savedTemplate
        every { templateRepository.findByIdAndProjectCode(any(), any()) } returns null
        every { templateRepository.findByIdAndProjectCode(anyLong(), any()) } returns null

        assertThrows<TemplateNotFoundException> {
            testService.modifyTemplate(2, "anyString", "anyString", "savedProject-finally")
        }

        verify(exactly = 1) { templateRepository.findByIdAndProjectCode(2, savedProject.code) }
    }
}