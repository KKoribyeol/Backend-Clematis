package com.dsm.kkoribyeol.domain.project.repository

import com.dsm.kkoribyeol.domain.project.repository.ProjectRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ProjectRepositoryTest(
    private val projectRepository: ProjectRepository,
) {

    @Test
    fun `existsByName - 이름이 일치하는 튜플이 존재합니다`() {
        val isExistProject = projectRepository.existsByName("savedProject")

        assertThat(isExistProject).isTrue
    }

    @Test
    fun `existsByName - 이름이 일치하는 튜플이 존재하지 않습니다`() {
        val isExistProject = projectRepository.existsByName("nonExistProject")

        assertThat(isExistProject).isFalse
    }
}