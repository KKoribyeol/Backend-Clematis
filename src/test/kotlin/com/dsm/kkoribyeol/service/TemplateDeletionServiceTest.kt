package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.TemplateNotFoundException
import com.dsm.kkoribyeol.repository.TemplateRepository
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

internal class TemplateDeletionServiceTest {
    private val templateRepository = mock(TemplateRepository::class.java)
    private val testService = TemplateDeletionService(templateRepository)

    @Test
    fun `푸시 템플릿 삭제하기 - 200 OK`() {
        whenever(templateRepository.existsById(1)).thenReturn(true)
        doNothing().whenever(templateRepository).deleteById(1)

        testService.delete(1)

        verify(templateRepository, times(1)).existsById(1)
        verify(templateRepository, times(1)).deleteById(1)
    }

    @Test
    fun `푸시 템플릿 삭제하기 - 404 TEMPLATE_NOT_FOUND`() {
        whenever(templateRepository.existsById(1)).thenReturn(true)
        whenever(templateRepository.existsById(anyLong())).thenReturn(false)

        assertThrows<TemplateNotFoundException> { testService.delete(2) }

        verify(templateRepository, times(1)).existsById(2)
        verify(templateRepository, never()).deleteById(anyLong())
    }
}