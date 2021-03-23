package com.dsm.kkoribyeol.domain.group.service

import com.dsm.kkoribyeol.domain.group.exception.GroupNotFoundException
import com.dsm.kkoribyeol.domain.group.repository.TargetGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GroupModificationService(
    private val groupRepository: TargetGroupRepository,
) {

    fun modifyGroupName(projectCode: String, groupName: String, newGroupName: String) =
        findGroup(
            projectCode = projectCode,
            groupName = groupName,
        ).modifyName(
            newGroupName = newGroupName,
        )

    private fun findGroup(projectCode: String, groupName: String) =
        groupRepository.findByProjectCodeAndAndGroupName(
            projectCode = projectCode,
            groupName = groupName,
        ) ?: throw GroupNotFoundException(projectCode, groupName)
}