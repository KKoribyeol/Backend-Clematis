package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.template.exception.TemplateNotFoundException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TemplateDeletionServiceTest {
    private val templateRepository = mockk<TemplateRepository>()
    private val testService = TemplateDeletionService(templateRepository)

    @Test
    fun `푸시 템플릿 삭제하기`() {
        every { templateRepository.existsByIdAndProjectCode(1, "savedProject-finally") } returns true
        every { templateRepository.deleteByIdAndProjectCode(1, "savedProject-finally") } just Runs

        testService.deleteTemplate(1, "savedProject-finally")

        verify(exactly = 1) { templateRepository.existsByIdAndProjectCode(1, "savedProject-finally") }
        verify(exactly = 1) { templateRepository.deleteByIdAndProjectCode(1, "savedProject-finally") }
    }

    @Test
    fun `푸시 템플릿 삭제하기 - throw TemplateNotFoundException`() {
        every { templateRepository.existsByIdAndProjectCode(1, "savedProject-finally") } returns true
        every { templateRepository.existsByIdAndProjectCode(any(), any()) } returns false
        every { templateRepository.deleteByIdAndProjectCode(1, "savedProject-finally") } just Runs

        assertThrows<TemplateNotFoundException> {
            testService.deleteTemplate(2, "savedProject-finally")
        }

        verify(exactly = 1) { templateRepository.existsByIdAndProjectCode(2, "savedProject-finally") }
        verify(exactly = 0) { templateRepository.deleteByIdAndProjectCode(any(), any()) }
    }
}