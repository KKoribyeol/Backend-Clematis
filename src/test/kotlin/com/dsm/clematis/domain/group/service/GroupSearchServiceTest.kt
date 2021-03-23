package com.dsm.clematis.domain.group.service

import com.dsm.clematis.domain.target.domain.Target
import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.affiliation.domain.TargetAffiliation
import com.dsm.clematis.domain.group.domain.TargetGroup
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.group.exception.GroupNotFoundException
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import com.dsm.clematis.domain.group.repository.TargetGroupRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GroupSearchServiceTest {
    private val groupRepository = mockk<TargetGroupRepository>()
    private val affiliationRepository = mockk<TargetAffiliationRepository>()
    private val testService = GroupSearchService(
        groupRepository = groupRepository,
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
    private val nonExistProject = Project(
        code = "nonExistProject-finally",
        name = "nonExistProject",
        description = "nonExistDescription",
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
    private val savedTarget = Target(
        token = "savedToken",
        nickname = "savedNickname",
        name = "savedName",
        project = savedProject,
    )
    private val savedAffiliation = TargetAffiliation(
        target = savedTarget,
        group = savedGroup,
    )

    @Test
    fun `전체 그룹 조회하기`() {
        every { groupRepository.findByProjectCode(savedProject.code) } returns listOf(savedGroup)
        every { groupRepository.findByProjectCode(nonExistProject.code) } returns listOf()

        val multipleGroup = testService.searchAllGroup(
            projectCode = "savedProject-finally",
        )

        assertThat(multipleGroup).containsAll(listOf(savedGroup))

        verify(exactly = 1) { groupRepository.findByProjectCode(savedProject.code) }
    }

    @Test
    fun `단일 그룹 조회하기`() {
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) } returns true
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) } returns false
        every { affiliationRepository.findByGroupProjectCodeAndGroupGroupName(savedProject.code, savedGroup.groupName) } returns listOf(savedAffiliation)

        val findAffiliation = testService.searchTargetInGroup(
            projectCode = "savedProject-finally",
            groupName = "savedName",
        )

        assertThat(findAffiliation).containsAll(listOf(savedAffiliation))

        verify(exactly = 1) { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) }
        verify(exactly = 1) { affiliationRepository.findByGroupProjectCodeAndGroupGroupName(savedProject.code, savedGroup.groupName) }
    }

    @Test
    fun `단일 그룹 조회하기 - throw GroupNotFoundException`() {
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) } returns true
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) } returns false
        every { affiliationRepository.findByGroupProjectCodeAndGroupGroupName(savedProject.code, savedGroup.groupName) } returns listOf(savedAffiliation)

        assertThrows<GroupNotFoundException> {
            testService.searchTargetInGroup(
                projectCode = "savedProject-finally",
                groupName = "nonExistName",
            )
        }

        verify(exactly = 1) { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) }
        verify(exactly = 0) { affiliationRepository.findByGroupProjectCodeAndGroupGroupName(any(), any()) }
    }
}