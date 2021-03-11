package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.TemplateNotFoundException
import com.dsm.kkoribyeol.repository.TemplateRepository
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

        testService.delete(1)

        verify(exactly = 1) { templateRepository.existsById(1) }
        verify(exactly = 1) { templateRepository.deleteById(1) }
    }

    @Test
    fun `푸시 템플릿 삭제하기 - throw TemplateNotFoundException`() {
        every { templateRepository.existsById(1) } returns true
        every { templateRepository.existsById(any()) } returns false

        assertThrows<TemplateNotFoundException> { testService.delete(2) }

        verify(exactly = 1) { templateRepository.existsById(2) }
        verify(exactly = 0) { templateRepository.deleteById(any()) }
    }
}