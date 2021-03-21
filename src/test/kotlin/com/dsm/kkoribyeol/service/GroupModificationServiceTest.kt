package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.domain.Project
import com.dsm.kkoribyeol.domain.TargetGroup
import com.dsm.kkoribyeol.exception.GroupNotFoundException
import com.dsm.kkoribyeol.repository.TargetGroupRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GroupModificationServiceTest {
    private val groupRepository = mockk<TargetGroupRepository>()
    private val testService = GroupModificationService(
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

    @Test
    fun `타겟 그룹 이름 변경하기`() {
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        testService.modifyGroupName(
            projectCode = "savedProject-finally",
            groupName = "savedName",
            newGroupName = "newName",
        )

        verify(exactly = 1) { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, "savedName") }
    }

    @Test
    fun `타겟 그룹 이름 변경하기 - throw GroupNotFoundException`() {
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, savedGroup.groupName) } returns savedGroup
        every { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) } returns null

        assertThrows<GroupNotFoundException> {
            testService.modifyGroupName(
                projectCode = "savedProject-finally",
                groupName = "nonExistName",
                newGroupName = "newName",
            )
        }

        verify(exactly = 1) { groupRepository.findByProjectCodeAndAndGroupName(savedProject.code, nonExistGroup.groupName) }
    }
}