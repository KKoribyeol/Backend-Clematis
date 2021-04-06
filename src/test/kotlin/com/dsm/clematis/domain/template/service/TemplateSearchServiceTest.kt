package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
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
import org.mockito.Mockito.anyLong

internal class TemplateSearchServiceTest {
    private val templateRepository = mockk<TemplateRepository>()
    private val testService = TemplateSearchService(templateRepository)

    private val savedAccount = Account("savedIdId", "savedPassword", "savedName")
    private val savedProject = Project("savedProject-finally", "savedProject", "savedDescription", savedAccount)
    private val savedTemplate = Template("saved title", "saved body", savedProject)

    @BeforeEach
    fun setup() {
        savedTemplate.id = 1
    }

    @Test
    fun `푸시 템플릿 단일 조회하기`() {
        every { templateRepository.findByIdAndProjectCode(1, savedProject.code) } returns savedTemplate
        every { templateRepository.findByIdAndProjectCode(anyLong(), any()) } returns null

        val findTemplate = testService.searchTemplate(1, "savedProject-finally")

        assertThat(findTemplate.id).isEqualTo(savedTemplate.id)
        assertThat(findTemplate.title).isEqualTo(savedTemplate.title)
        assertThat(findTemplate.body).isEqualTo(savedTemplate.body)

        verify(exactly = 1) { templateRepository.findByIdAndProjectCode(1, savedProject.code) }
        verify(exactly = 0) { templateRepository.findByProjectCode(any()) }
    }

    @Test
    fun `푸시 템플릿 단일 조회하기 - throw TemplateNotFoundException`() {
        every { templateRepository.findByIdAndProjectCode(1, savedProject.code) } returns savedTemplate
        every { templateRepository.findByIdAndProjectCode(any(), any()) } returns null
        every { templateRepository.findByIdAndProjectCode(anyLong(), any()) } returns null

        assertThrows<TemplateNotFoundException> {
            testService.searchTemplate(2, "savedProject-finally")
        }

        verify(exactly = 1) { templateRepository.findByIdAndProjectCode(2, savedProject.code) }
        verify(exactly = 0) { templateRepository.findByProjectCode(any()) }
    }

    @Test
    fun `푸시 템플릿 전체 조회하기`() {
        every { templateRepository.findByProjectCode(savedProject.code) } returns listOf(savedTemplate)

        val findTemplates = testService.searchAllTemplate("savedProject-finally")

        assertThat(findTemplates).map<String>{ it.title }.containsAll(listOf(savedTemplate.title))
        assertThat(findTemplates).map<String>{ it.body }.containsAll(listOf(savedTemplate.body))

        verify(exactly = 0) { templateRepository.findByIdAndProjectCode(anyLong(), any()) }
        verify(exactly = 1) { templateRepository.findByProjectCode(savedProject.code) }
    }
}