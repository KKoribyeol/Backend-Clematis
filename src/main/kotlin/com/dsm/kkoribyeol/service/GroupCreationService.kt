package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.TargetGroup
import com.dsm.kkoribyeol.exception.AlreadyExistGroupException
import com.dsm.kkoribyeol.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.repository.ProjectRepository
import com.dsm.kkoribyeol.repository.TargetGroupRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GroupCreationService(
    private val groupRepository: TargetGroupRepository,
    private val projectRepository: ProjectRepository,
) {

    fun createGroup(projectCode: String, groupName: String) {
        if (isExistGroup(projectCode, groupName))
            throw AlreadyExistGroupException(projectCode, groupName)
        else
            save(
                projectCode = projectCode,
                groupName = groupName,
            )
    }

    private fun isExistGroup(projectCode: String, groupName: String) =
        groupRepository.existsByProjectCodeAndGroupName(
            projectCode = projectCode,
            groupName = groupName,
        )

    private fun save(projectCode: String, groupName: String) =
        groupRepository.save(
            TargetGroup(
                project = findProjectByCode(projectCode),
                groupName = groupName,
            )
        )

    private fun findProjectByCode(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode) ?: throw ProjectNotFoundException(projectCode)
}