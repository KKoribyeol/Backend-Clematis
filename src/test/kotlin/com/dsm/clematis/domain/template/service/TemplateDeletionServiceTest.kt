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
        every { templateRepository.existsById(1) } returns true
        every { templateRepository.deleteById(1) } just Runs

        testService.deleteTemplate(1)

        verify(exactly = 1) { templateRepository.existsById(1) }
        verify(exactly = 1) { templateRepository.deleteById(1) }
    }

    @Test
    fun `푸시 템플릿 삭제하기 - throw TemplateNotFoundException`() {
        every { templateRepository.existsById(1) } returns true
        every { templateRepository.existsById(any()) } returns false

        assertThrows<TemplateNotFoundException> { testService.deleteTemplate(2) }

        verify(exactly = 1) { templateRepository.existsById(2) }
        verify(exactly = 0) { templateRepository.deleteById(any()) }
    }
}