package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.affiliation.domain.TargetAffiliation
import com.dsm.clematis.domain.affiliation.exception.AlreadyExistAffiliationException
import com.dsm.clematis.domain.group.exception.GroupNotFoundException
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import com.dsm.clematis.domain.group.repository.TargetGroupRepository
import com.dsm.clematis.domain.target.exception.TargetNotFoundException
import com.dsm.clematis.domain.target.repository.TargetRepository
import org.springframework.stereotype.Service

@Service
class TargetGroupingService(
    private val affiliationRepository: TargetAffiliationRepository,
    private val targetRepository: TargetRepository,
    private val groupRepository: TargetGroupRepository,
) {

    fun groupTarget(
        projectCode: String,
        groupName: String,
        targetToken: String,
    ) {
        if (isExistAffiliation(projectCode, groupName, targetToken))
            throw AlreadyExistAffiliationException()
        else
            save(projectCode, groupName, targetToken)
    }

    private fun isExistAffiliation(
        projectCode: String,
        groupName: String,
        targetToken: String,
    ) = affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
        projectCode = projectCode,
        groupName = groupName,
        targetToken = targetToken,
    )

    private fun save(projectCode: String, groupName: String, targetToken: String) =
        affiliationRepository.save(
            TargetAffiliation(
                target = findTargetByToken(projectCode, targetToken),
                group = findGroupByName(projectCode, groupName),
            )
        )

    private fun findTargetByToken(projectCode: String, targetToken: String) =
        targetRepository.findByProjectCodeAndToken(projectCode, targetToken) ?: throw TargetNotFoundException(targetToken)

    private fun findGroupByName(projectCode: String, groupName: String) =
        groupRepository.findByProjectCodeAndAndGroupName(projectCode, groupName)
            ?: throw GroupNotFoundException(projectCode, groupName)
}