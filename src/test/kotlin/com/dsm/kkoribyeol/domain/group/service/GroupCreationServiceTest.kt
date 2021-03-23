package com.dsm.kkoribyeol.domain.group.service

import com.dsm.kkoribyeol.domain.account.domain.Account
import com.dsm.kkoribyeol.domain.project.domain.Project
import com.dsm.kkoribyeol.domain.group.domain.TargetGroup
import com.dsm.kkoribyeol.domain.group.exception.AlreadyExistGroupException
import com.dsm.kkoribyeol.domain.project.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.domain.project.repository.ProjectRepository
import com.dsm.kkoribyeol.domain.group.repository.TargetGroupRepository
import com.dsm.kkoribyeol.domain.group.service.GroupCreationService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class GroupCreationServiceTest {
    private val groupRepository = mockk<TargetGroupRepository>()
    private val projectRepository = mockk<ProjectRepository>()
    private val testService = GroupCreationService(
        groupRepository = groupRepository,
        projectRepository = projectRepository,
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
    fun `타겟 그룹 생성하기`() {
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) } returns true
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) } returns false
        every { groupRepository.save(any()) } returns savedGroup
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        testService.createGroup(
            projectCode = "savedProject-finally",
            groupName = "nonExistName",
        )

        verify(exactly = 1) { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) }
        verify(exactly = 1) { groupRepository.save(any()) }
        verify(exactly = 1) { projectRepository.findByIdOrNull(savedProject.code) }
    }

    @Test
    fun `타겟 그룹 생성하기 - throw AlreadyExistGroupException`() {
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) } returns true
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) } returns false
        every { groupRepository.save(any()) } returns savedGroup
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        assertThrows<AlreadyExistGroupException> {
            testService.createGroup(
                projectCode = "savedProject-finally",
                groupName = "savedName",
            )
        }

        verify(exactly = 1) { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) }
        verify(exactly = 0) { groupRepository.save(any()) }
        verify(exactly = 0) { projectRepository.findByIdOrNull(savedProject.code) }
    }

    @Test
    fun `타겟 그룹 생성하기 - throw ProjectNotFoundException`() {
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, savedGroup.groupName) } returns true
        every { groupRepository.existsByProjectCodeAndGroupName(savedProject.code, nonExistGroup.groupName) } returns false
        every { groupRepository.existsByProjectCodeAndGroupName(nonExistProject.code, nonExistGroup.groupName) } returns false
        every { groupRepository.save(any()) } returns savedGroup
        every { projectRepository.findByIdOrNull(savedProject.code) } returns savedProject
        every { projectRepository.findByIdOrNull(nonExistProject.code) } returns null

        assertThrows<ProjectNotFoundException> {
            testService.createGroup(
                projectCode = "nonExistProject-finally",
                groupName = "nonExistName",
            )
        }

        verify(exactly = 1) { groupRepository.existsByProjectCodeAndGroupName(nonExistProject.code, nonExistGroup.groupName) }
        verify(exactly = 0) { groupRepository.save(any()) }
        verify(exactly = 1) { projectRepository.findByIdOrNull(nonExistProject.code) }
    }
}