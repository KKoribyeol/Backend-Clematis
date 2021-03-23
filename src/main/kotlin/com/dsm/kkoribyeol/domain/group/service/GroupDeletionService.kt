package com.dsm.kkoribyeol.domain.group.service

import com.dsm.kkoribyeol.domain.group.exception.GroupNotFoundException
import com.dsm.kkoribyeol.domain.group.repository.TargetGroupRepository
import org.springframework.stereotype.Service

@Service
class GroupDeletionService(
    private val groupRepository: TargetGroupRepository,
) {

    fun deleteGroup(projectCode: String, groupName: String) =
        if (isExistGroup(projectCode, groupName))
            groupRepository.deleteByProjectCodeAndAndGroupName(projectCode, groupName)
        else
            throw throw GroupNotFoundException(projectCode, groupName)

    private fun isExistGroup(projectCode: String, groupName: String) =
        groupRepository.existsByProjectCodeAndGroupName(
            projectCode = projectCode,
            groupName = groupName,
        )
}