package com.dsm.clematis.domain.affiliation.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class TargetAffiliationRepositoryTest(
    private val affiliationRepository: TargetAffiliationRepository,
) {

    @Test
    fun `existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn - return true`() {
        val isExistAffiliation =
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = "savedProject-finally",
                groupName = "savedName",
                targetTokens = listOf("savedToken"),
            )

        assertTrue(isExistAffiliation)
    }

    @Test
    fun `existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn - return false`() {
        val isExistAffiliation =
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = "nonExistProject-finally",
                groupName = "nonExistName",
                targetTokens = listOf("nonExistToken"),
            )

        assertFalse(isExistAffiliation)
    }

    @Test
    fun `existsByGroupProjectCodeAndGroupGroupNameAndTargetToken - return true`() {
        val isExistAffiliation =
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = "savedProject-finally",
                groupName = "savedName",
                targetToken = "savedToken",
            )

        assertTrue(isExistAffiliation)
    }

    @Test
    fun `existsByGroupProjectCodeAndGroupGroupNameAndTargetToken - return false`() {
        val isExistAffiliation =
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = "nonExistProject-finally",
                groupName = "nonExistName",
                targetToken = "nonExistToken",
            )

        assertFalse(isExistAffiliation)
    }

    @Test
    fun `findByGroupProjectCodeAndGroupGroupName - return List{TargetAffiliation}`() {
        val affiliation = affiliationRepository.findByGroupProjectCodeAndGroupGroupName(
            projectCode = "savedProject-finally",
            groupName = "savedName",
        )

        assertThat(affiliation).map<String> { it.target.token }.containsAll(listOf("savedToken"))
        assertThat(affiliation).map<String> { it.group.groupName }.containsAll(listOf("savedName"))
    }

    @Test
    fun `findByGroupProjectCodeAndGroupGroupName - return List{}`() {
        val affiliation = affiliationRepository.findByGroupProjectCodeAndGroupGroupName(
            projectCode = "nonExistProject-finally",
            groupName = "nonExistName",
        )

        affiliation.isEmpty()
    }

    @Test
    fun `deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken`() {
        affiliationRepository.deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken(
            projectCode = "savedProject-finally",
            groupName = "savedName",
            targetToken = "savedToken",
        )
    }
}