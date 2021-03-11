package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Template
import com.dsm.kkoribyeol.exception.TemplateNotFoundException
import com.dsm.kkoribyeol.repository.TemplateRepository
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

    private val savedTemplate = Template("saved title", "saved body")

    @BeforeEach
    fun setup() {
        savedTemplate.id = 1
    }

    @Test
    fun `푸시 템플릿 수정하기`() {
        every { templateRepository.findByIdOrNull(1) } returns savedTemplate
        every { templateRepository.findByIdOrNull(anyLong()) } returns null

        testService.modify(1, "modified title", "modified body")

        verify(exactly = 1) { templateRepository.findByIdOrNull(1) }
    }

    @Test
    fun `푸시 템플릿 수정하기 - throw TemplateNotFoundException`() {
        every { templateRepository.findByIdOrNull(1) } returns savedTemplate
        every { templateRepository.findByIdOrNull(2) } returns null

        assertThrows<TemplateNotFoundException> { testService.modify(2, "anyString", "anyString") }

        verify(exactly = 1) { templateRepository.findByIdOrNull(2) }
    }
}