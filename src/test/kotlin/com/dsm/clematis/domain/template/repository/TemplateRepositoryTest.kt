package com.dsm.clematis.domain.template.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class TemplateRepositoryTest(
    private val templateRepository: TemplateRepository,
) {

    @Test
    fun `existsByTitleAndBody - 제목과 내용이 존재합니다`() {
        val isExistTemplate =
            templateRepository.existsByTitleAndBodyAndProjectCode(
                title = "savedTitle",
                body = "savedBody",
                projectCode = "savedProject-finally",
            )

        assertThat(isExistTemplate).isTrue
    }
    
    @Test
    fun `existsByTitleAndBody - 제목과 내용이 존재하지 않습니다`() {
        val isExistTemplate = templateRepository.existsByTitleAndBodyAndProjectCode(
            title = "nonExistTitle",
            body = "nonExistBody",
            projectCode = "savedProject-finally",
        )

        assertThat(isExistTemplate).isFalse
    }
}