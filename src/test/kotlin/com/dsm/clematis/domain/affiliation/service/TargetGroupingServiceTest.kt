package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.affiliation.domain.TargetAffiliation
import com.dsm.clematis.domain.affiliation.exception.AlreadyExistAffiliationException
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import com.dsm.clematis.domain.group.domain.TargetGroup
import com.dsm.clematis.domain.group.exception.GroupNotFoundException
import com.dsm.clematis.domain.group.repository.TargetGroupRepository
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.target.domain.Target
import com.dsm.clematis.domain.target.repository.TargetRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TargetGroupingServiceTest {
    private val affiliationRepository = mockk<TargetAffiliationRepository>()
    private val targetRepository = mockk<TargetRepository>()
    private val groupRepository = mockk<TargetGroupRepository>()
    private val testService = TargetGroupingService(
        affiliationRepository = affiliationRepository,
        targetRepository = targetRepository,
        groupRepository = groupRepository,
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
    private val nonExistGroup = TargetGroup(
        groupName = "nonExistName",
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
    private val nonExistAffiliation = TargetAffiliation(
        target = unaffiliatedTarget,
        group = savedGroup,
    )

    @Test
    fun `타겟을 그룹에 소속시키기`() {
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = affiliatedTarget.token,
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = any(),
                groupName = any(),
                targetToken = unaffiliatedTarget.token,
            )
        } returns false
        every { affiliationRepository.save(nonExistAffiliation) } returns nonExistAffiliation
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, unaffiliatedTarget.token) } returns unaffiliatedTarget
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        testService.groupTarget(
            projectCode = "savedProject-finally",
            groupName = "savedName",
            targetToken = "unaffiliatedToken",
        )

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                savedProject.code,
                savedGroup.groupName,
                unaffiliatedTarget.token,
            )
        }
        verify(exactly = 1) { affiliationRepository.save(nonExistAffiliation) }
        verify(exactly = 1) { targetRepository.findByProjectCodeAndToken(savedProject.code, unaffiliatedTarget.token) }
        verify(exactly = 1) { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) }
    }

    @Test
    fun `타겟을 그룹에 소속시키기 - throw AlreadyExistAffiliationException`() {
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = affiliatedTarget.token,
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = any(),
                groupName = any(),
                targetToken = unaffiliatedTarget.token,
            )
        } returns false
        every { affiliationRepository.save(nonExistAffiliation) } returns nonExistAffiliation
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, unaffiliatedTarget.token) } returns unaffiliatedTarget
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        assertThrows<AlreadyExistAffiliationException> {
            testService.groupTarget(
                projectCode = "savedProject-finally",
                groupName = "savedName",
                targetToken = "affiliatedToken",
            )
        }

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                savedProject.code,
                savedGroup.groupName,
                affiliatedTarget.token,
            )
        }
        verify(exactly = 0) { affiliationRepository.save(any()) }
        verify(exactly = 0) { targetRepository.findByProjectCodeAndToken(any(), any()) }
        verify(exactly = 0) { groupRepository.findByProjectCodeAndAndGroupName(any(), any()) }
    }

    @Test
    fun `타겟을 그룹에 소속시키기 - throw GroupNotFoundException`() {
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetToken = affiliatedTarget.token,
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                projectCode = any(),
                groupName = any(),
                targetToken = unaffiliatedTarget.token,
            )
        } returns false
        every { affiliationRepository.save(nonExistAffiliation) } returns nonExistAffiliation
        every { targetRepository.findByProjectCodeAndToken(savedProject.code, unaffiliatedTarget.token) } returns unaffiliatedTarget
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        assertThrows<GroupNotFoundException> {
            testService.groupTarget(
                projectCode = "savedProject-finally",
                groupName = "nonExistName",
                targetToken = "unaffiliatedToken",
            )
        }

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
                savedProject.code,
                nonExistGroup.groupName,
                unaffiliatedTarget.token,
            )
        }
        verify(exactly = 0) { affiliationRepository.save(any()) }
        verify(exactly = 1) { targetRepository.findByProjectCodeAndToken(any(), any()) }
        verify(exactly = 1) { groupRepository.findByProjectCodeAndAndGroupName(any(), any()) }
    }
}