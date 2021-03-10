package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Template
import com.dsm.kkoribyeol.repository.TemplateRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class TemplateCreationServiceTest {

    @Test
    fun `푸시 템플릿 생성하기 - 200 OK`() {
        val creationNumber = templateCreationService.create(templateTitle = "title", templateBody = "body")

        assertThat(creationNumber).isEqualTo(2)
        verify(templateRepository, times(1)).save(any(Template::class.java))
    }

    private val templateRepository: TemplateRepository = mock {
        val template = Template("title", "body")
        template.id = 2

        on {
            save(any(Template::class.java))
        }.thenReturn(template)
    }

    private val templateCreationService = TemplateCreationService(
        templateRepository = templateRepository
    )
}