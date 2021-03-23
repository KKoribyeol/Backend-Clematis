package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.affiliation.exception.AffiliationNotFoundException
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import com.dsm.clematis.domain.group.domain.TargetGroup
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.target.domain.Target
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TargetUngroupingServiceTest {
    private val affiliationRepository = mockk<TargetAffiliationRepository>()
    private val testService = TargetUngroupingService(
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
    private val unaffiliatedTarget = Target(
        token = "unaffiliatedToken",
        nickname = "unaffiliatedNickname",
        name = "unaffiliatedName",
        project = savedProject,
    )
    private val affiliatedTarget = Target(
        token = "affiliatedToken",
        nickname = "affiliatedNickname",
        name = "affiliatedName",
        project = savedProject,
    )

    @Test
    fun `타겟을 그룹에서 해제하기`() {
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = affiliatedTarget.token,
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = unaffiliatedTarget.token,
            )
        } returns false
        every {
            affiliationRepository.deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = any(),
                groupName = any(),
                targetToken = any(),
            )
        } just Runs

        testService.ungroupTarget(
            projectCode = "savedProject-finally",
            groupName = "savedName",
            targetToken = "affiliatedToken",
        )

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = affiliatedTarget.token,
            )
        }

        verify(exactly = 1) {
            affiliationRepository.deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = affiliatedTarget.token,
            )
        }
    }

    @Test
    fun `타겟을 그룹에서 해제하기 - throw AffiliationNotFoundException`() {
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = affiliatedTarget.token,
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = unaffiliatedTarget.token,
            )
        } returns false
        every {
            affiliationRepository.deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = any(),
                groupName = any(),
                targetToken = any(),
            )
        } just Runs

        assertThrows<AffiliationNotFoundException> {
            testService.ungroupTarget(
                projectCode = "savedProject-finally",
                groupName = "savedName",
                targetToken = "unaffiliatedToken",
            )
        }

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = unaffiliatedTarget.token,
            )
        }

        verify(exactly = 0) {
            affiliationRepository.deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = any(),
                groupName = any(),
                targetToken = any(),
            )
        }
    }
}