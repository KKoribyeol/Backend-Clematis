package com.dsm.clematis.domain.group.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class TargetGroupRepositoryTest(
    private val groupRepository: TargetGroupRepository,
) {

    @Test
    fun `existsByProjectCodeAndGroupName - 그룹이 존재함`() {
        val isExistGroup = groupRepository.existsByProjectCodeAndGroupName(
            projectCode = "savedProject-finally",
            groupName = "savedName",
        )

        assertThat(isExistGroup).isTrue
    }
    
    @Test
    fun `existsByProjectCodeAndGroupName - 그룹이 존재하지 않음`() {
        val isExistGroup = groupRepository.existsByProjectCodeAndGroupName(
            projectCode = "nonExistProject-finally",
            groupName = "nonExistName",
        )

        assertThat(isExistGroup).isFalse
    }

    @Test
    fun `findByProjectCodeAndAndGroupName - return TargetGroup`() {
        val findTargetGroup = groupRepository.findByProjectCodeAndAndGroupName(
            projectCode = "savedProject-finally",
            groupName = "savedName",
        )

        assertThat(findTargetGroup?.id).isEqualTo(1)
        assertThat(findTargetGroup?.groupName).isEqualTo("savedName")
        assertThat(findTargetGroup?.project?.code).isEqualTo("savedProject-finally")
    }

    @Test
    fun `findByProjectCodeAndAndGroupName - return null`() {
        val findTargetGroup = groupRepository.findByProjectCodeAndAndGroupName(
            projectCode = "nonExistProject-finally",
            groupName = "nonExistName",
        )

        assertThat(findTargetGroup).isNull()
    }

    @Test
    fun `findByProjectCode - return List{TargetGroup}`() {
        val groups = groupRepository.findByProjectCode(
            projectCode = "savedProject-finally",
        )

        assertThat(groups).map<Long?> { it.id }.containsAll(listOf(1))
        assertThat(groups).map<String> { it.groupName }.containsAll(listOf("savedName"))
        assertThat(groups).map<String> { it.project.code }.containsAll(listOf("savedProject-finally"))
    }

    @Test
    fun `findByProjectCode - return List{}`() {
        val groups = groupRepository.findByProjectCode(
            projectCode = "nonExistProject-finally",
        )

        assertThat(groups).isEmpty()
    }

    @Test
    fun `deleteByProjectCodeAndAndGroupName`() {
        groupRepository.deleteByProjectCodeAndAndGroupName(
            projectCode = "savedProject-finally",
            groupName = "savedName",
        )
    }
}