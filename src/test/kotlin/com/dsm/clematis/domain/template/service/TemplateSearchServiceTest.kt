package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.template.domain.Template
import com.dsm.clematis.domain.template.exception.TemplateNotFoundException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import org.mockito.Mockito.anyLong

internal class TemplateSearchServiceTest {
    private val templateRepository = mockk<TemplateRepository>()
    private val testService = TemplateSearchService(templateRepository)

    private val savedTemplate = Template("saved title", "saved body")

    @BeforeEach
    fun setup() {
        savedTemplate.id = 1
    }

    @Test
    fun `푸시 템플릿 단일 조회하기`() {
        every { templateRepository.findByIdOrNull(1) } returns savedTemplate
        every { templateRepository.findByIdOrNull(anyLong()) } returns null

        val findTemplate = testService.searchTemplate(1)

        assertThat(findTemplate.id).isEqualTo(savedTemplate.id)
        assertThat(findTemplate.title).isEqualTo(savedTemplate.title)
        assertThat(findTemplate.body).isEqualTo(savedTemplate.body)

        verify(exactly = 1) { templateRepository.findByIdOrNull(1) }
        verify(exactly = 0) { templateRepository.findAll() }
    }

    @Test
    fun `푸시 템플릿 단일 조회하기 - throw TemplateNotFoundException`() {
        every { templateRepository.findByIdOrNull(1) } returns savedTemplate
        every { templateRepository.findByIdOrNull(2) } returns null

        assertThrows<TemplateNotFoundException> { testService.searchTemplate(2) }

        verify(exactly = 1) { templateRepository.findByIdOrNull(2) }
        verify(exactly = 0) { templateRepository.findAll() }
    }

    @Test
    fun `푸시 템플릿 전체 조회하기`() {
        every { templateRepository.findAll() } returns listOf(savedTemplate)

        val findTemplates = testService.searchAllTemplate()

        assertThat(findTemplates).map<String>{ it.title }.containsAll(listOf(savedTemplate.title))
        assertThat(findTemplates).map<String>{ it.body }.containsAll(listOf(savedTemplate.body))

        verify(exactly = 0) { templateRepository.findByIdOrNull(anyLong()) }
        verify(exactly = 1) { templateRepository.findAll() }
    }
}