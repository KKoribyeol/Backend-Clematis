package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.affiliation.exception.AffiliationNotFoundException
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import org.springframework.stereotype.Service

@Service
class TargetUngroupingService(
    private val affiliationRepository: TargetAffiliationRepository,
) {

    fun ungroupTarget(
        projectCode: String,
        groupName: String,
        targetToken: String,
    ) = if (isExistAffiliation(projectCode, groupName, targetToken))
        deleteAffiliation(projectCode, groupName, targetToken)
    else
        throw AffiliationNotFoundException(groupName, targetToken)

    private fun isExistAffiliation(
        projectCode: String,
        groupName: String,
        targetToken: String,
    ) = affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(
        projectCode = projectCode,
        groupName = groupName,
        targetToken = targetToken,
    )

    private fun deleteAffiliation(
        projectCode: String,
        groupName: String,
        targetToken: String,
    ) = affiliationRepository.deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken(
        projectCode = projectCode,
        groupName = groupName,
        targetToken = targetToken,
    )
}