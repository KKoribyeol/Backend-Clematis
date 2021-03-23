package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.affiliation.domain.TargetAffiliation
import com.dsm.clematis.domain.affiliation.exception.AlreadyExistAffiliationException
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepositoryTest
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
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetTokens = listOf(affiliatedTarget.token),
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = any(),
                groupName = any(),
                targetTokens = listOf(unaffiliatedTarget.token),
            )
        } returns false
        every { affiliationRepository.saveAll(listOf(nonExistAffiliation)) } returns listOf(nonExistAffiliation)
        every { targetRepository.findByProjectCodeAndTokenIn(savedProject.code, listOf(unaffiliatedTarget.token)) } returns listOf(unaffiliatedTarget)
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        testService.groupTarget(
            projectCode = "savedProject-finally",
            groupName = "savedName",
            targetTokens = listOf("unaffiliatedToken"),
        )

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                savedProject.code,
                savedGroup.groupName,
                listOf(unaffiliatedTarget.token),
            )
        }
        verify(exactly = 1) { affiliationRepository.saveAll(listOf(nonExistAffiliation)) }
        verify(exactly = 1) { targetRepository.findByProjectCodeAndTokenIn(savedProject.code, listOf(unaffiliatedTarget.token)) }
        verify(exactly = 1) { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) }
    }

    @Test
    fun `타겟을 그룹에 소속시키기 - throw AlreadyExistAffiliationException`() {
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetTokens = listOf(affiliatedTarget.token),
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = any(),
                groupName = any(),
                targetTokens = listOf(unaffiliatedTarget.token),
            )
        } returns false
        every { affiliationRepository.saveAll(listOf(nonExistAffiliation)) } returns listOf(nonExistAffiliation)
        every { targetRepository.findByProjectCodeAndTokenIn(savedProject.code, listOf(unaffiliatedTarget.token)) } returns listOf(unaffiliatedTarget)
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        assertThrows<AlreadyExistAffiliationException> {
            testService.groupTarget(
                projectCode = "savedProject-finally",
                groupName = "savedName",
                targetTokens = listOf("affiliatedToken"),
            )
        }

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                savedProject.code,
                savedGroup.groupName,
                listOf(affiliatedTarget.token),
            )
        }
        verify(exactly = 0) { affiliationRepository.saveAll(listOf(nonExistAffiliation)) }
        verify(exactly = 0) { targetRepository.findByProjectCodeAndTokenIn(any(), listOf(affiliatedTarget.token)) }
        verify(exactly = 0) { groupRepository.findByProjectCodeAndAndGroupName(any(), any()) }
    }

    @Test
    fun `타겟을 그룹에 소속시키기 - throw GroupNotFoundException`() {
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = savedProject.code,
                groupName = savedGroup.groupName,
                targetTokens = listOf(affiliatedTarget.token),
            )
        } returns true
        every {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                projectCode = any(),
                groupName = any(),
                targetTokens = listOf(unaffiliatedTarget.token),
            )
        } returns false
        every { affiliationRepository.saveAll(listOf(nonExistAffiliation)) } returns listOf(nonExistAffiliation)
        every { targetRepository.findByProjectCodeAndTokenIn(savedProject.code, listOf(unaffiliatedTarget.token)) } returns listOf(unaffiliatedTarget)
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        assertThrows<GroupNotFoundException> {
            testService.groupTarget(
                projectCode = "savedProject-finally",
                groupName = "nonExistName",
                targetTokens = listOf("unaffiliatedToken"),
            )
        }

        verify(exactly = 1) {
            affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
                savedProject.code,
                nonExistGroup.groupName,
                listOf(unaffiliatedTarget.token),
            )
        }
        verify(exactly = 0) { affiliationRepository.saveAll(listOf(nonExistAffiliation)) }
        verify(exactly = 1) { targetRepository.findByProjectCodeAndTokenIn(any(), listOf(unaffiliatedTarget.token)) }
        verify(exactly = 1) { groupRepository.findByProjectCodeAndAndGroupName(any(), any()) }
    }
}