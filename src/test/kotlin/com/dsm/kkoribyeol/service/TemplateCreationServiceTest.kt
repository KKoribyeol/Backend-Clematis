package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Template
import com.dsm.kkoribyeol.exception.AlreadyExistTemplateException
import com.dsm.kkoribyeol.repository.TemplateRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

internal class TemplateCreationServiceTest {
    private val templateRepository = mock(TemplateRepository::class.java)
    private val testService = TemplateCreationService(templateRepository)

    private val alreadyExistTemplate = Template("already exist title", "already exist body")
    private val returnedTemplate = Template("title", "body")

    @BeforeEach
    fun setup() {
        returnedTemplate.id = 2
    }

    @Test
    fun `푸시 템플릿 생성하기 - 200 OK`() {
        whenever(templateRepository.existsByTitleAndBody(returnedTemplate.title, returnedTemplate.body)).thenReturn(false)
        whenever(templateRepository.existsByTitleAndBody(alreadyExistTemplate.title, alreadyExistTemplate.body)).thenReturn(true)
        whenever(templateRepository.save(any(Template::class.java))).thenReturn(returnedTemplate)

        val creationNumber = testService.create("title", "body")

        assertThat(creationNumber).isEqualTo(2)
        verify(templateRepository, times(1)).existsByTitleAndBody(anyString(), anyString())
        verify(templateRepository, times(1)).save(any(Template::class.java))
    }

    @Test
    fun `푸시 템플릿 생성하기 - 400 ALREADY_EXIST_TEMPLATE`() {
        whenever(templateRepository.existsByTitleAndBody(returnedTemplate.title, returnedTemplate.body)).thenReturn(false)
        whenever(templateRepository.existsByTitleAndBody(alreadyExistTemplate.title, alreadyExistTemplate.body)).thenReturn(true)
        whenever(templateRepository.save(any(Template::class.java))).thenReturn(returnedTemplate)

        assertThrows<AlreadyExistTemplateException> {
            testService.create("already exist title", "already exist body")
        }
        verify(templateRepository, times(1)).existsByTitleAndBody(anyString(), anyString())
        verify(templateRepository, never()).save(any())
    }
}