package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.affiliation.domain.TargetAffiliation
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import com.dsm.clematis.domain.group.domain.TargetGroup
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.target.domain.Target
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AffiliationSearchServiceTest {
    private val affiliationRepository = mockk<TargetAffiliationRepository>()
    private val testService = AffiliationSearchService(
        affiliationRepository = affiliationRepository,
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )
    private val savedProject = Project(
        code = "savedProject-finally",
        name = "savedProject",
        description = "savedDescription",
        owner = savedAccount,
    )
    private val savedGroup = TargetGroup(
        groupName = "savedName",
        project = savedProject,
    )
    private val affiliatedTarget = Target(
        token = "affiliatedToken",
        nickname = "affiliatedNickname",
        name = "affiliatedName",
        project = savedProject,
    )
    private val savedAffiliation = TargetAffiliation(
        target = affiliatedTarget,
        group = savedGroup,
    )

    @Test
    fun `그룹에 속하거나 토큰에 해당하는 타겟 찾기`() {
        every {
            affiliationRepository.findByGroupProjectCodeAndGroupGroupNameIn(
                projectCode = savedProject.code,
                groupNames = listOf(savedGroup.groupName),
            )
        } returns listOf(savedAffiliation)

        val affiliation = testService.getAffiliatedTarget(
            projectCode = "savedProject-finally",
            groupNames = listOf("savedName"),
            targetTokens = listOf("affiliatedToken"),
        )

        assertThat(affiliation).containsAll(listOf("affiliatedToken"))

        verify(exactly = 1) {
            affiliationRepository.findByGroupProjectCodeAndGroupGroupNameIn(
                projectCode = savedProject.code,
                groupNames = listOf(savedGroup.groupName),
            )
        }
    }
}