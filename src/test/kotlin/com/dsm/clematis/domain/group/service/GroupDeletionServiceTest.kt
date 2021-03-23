package com.dsm.clematis.domain.group.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.group.domain.TargetGroup
import com.dsm.clematis.domain.group.exception.GroupNotFoundException
import com.dsm.clematis.domain.group.repository.TargetGroupRepository
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GroupDeletionServiceTest {
    private val groupRepository = mockk<TargetGroupRepository>()
    private val testService = GroupDeletionService(
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

    @Test
    fun `그룹 삭제하기`() {
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) } returns true
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) } returns false
        every { groupRepository.deleteByProjectCodeAndAndGroupName(savedProject.code, any()) } just Runs

        testService.deleteGroup(
            projectCode = "savedProject-finally",
            groupName = "savedName",
        )

        verify(exactly = 1) { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) }
        verify(exactly = 1) { groupRepository.deleteByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) }
    }

    @Test
    fun `그룹 삭제하기 - throw GroupNotFoundException`() {
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) } returns true
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) } returns false
        every { groupRepository.deleteByProjectCodeAndAndGroupName(savedProject.code, any()) } just Runs

        assertThrows<GroupNotFoundException> {
            testService.deleteGroup(
                projectCode = "savedProject-finally",
                groupName = "nonExistName",
            )
        }

        verify(exactly = 1) { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) }
        verify(exactly = 0) { groupRepository.deleteByProjectCodeAndAndGroupName(any(), any()) }
    }
}