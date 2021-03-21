package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.GroupNotFoundException
import com.dsm.kkoribyeol.repository.TargetAffiliationRepository
import com.dsm.kkoribyeol.repository.TargetGroupRepository
import org.springframework.stereotype.Service

@Service
class GroupSearchService(
    private val groupRepository: TargetGroupRepository,
    private val affiliationRepository: TargetAffiliationRepository,
) {

    fun searchTargetInGroup(projectCode: String, groupName: String) =
        if (isExistGroup(projectCode, groupName))
            findAffiliationByGroup(
                projectCode = projectCode,
                groupName = groupName,
            )
        else
            throw GroupNotFoundException(projectCode, groupName)

    private fun isExistGroup(projectCode: String, groupName: String) =
        groupRepository.existsByProjectCodeAndGroupName(
            projectCode = projectCode,
            groupName = groupName,
        )

    private fun findAffiliationByGroup(projectCode: String, groupName: String) =
        affiliationRepository.findByGroupProjectCodeAndGroupGroupName(
            projectCode = projectCode,
            groupName = groupName,
        )

    fun searchAllGroup(projectCode: String) =
        groupRepository.findByProjectCode(
            projectCode = projectCode,
        )
}