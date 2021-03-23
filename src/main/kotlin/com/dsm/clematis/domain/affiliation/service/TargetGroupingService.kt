package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.affiliation.domain.TargetAffiliation
import com.dsm.clematis.domain.affiliation.exception.AlreadyExistAffiliationException
import com.dsm.clematis.domain.group.exception.GroupNotFoundException
import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import com.dsm.clematis.domain.group.repository.TargetGroupRepository
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
        targetTokens: List<String>,
    ) {
        if (isExistAffiliation(projectCode, groupName, targetTokens))
            save(projectCode, groupName, targetTokens)
        else
            throw AlreadyExistAffiliationException()
    }

    private fun isExistAffiliation(
        projectCode: String,
        groupName: String,
        targetTokens: List<String>,
    ) = affiliationRepository.existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(
        projectCode = projectCode,
        groupName = groupName,
        targetTokens = targetTokens,
    )

    private fun save(projectCode: String, groupName: String, targetTokens: List<String>) =
        affiliationRepository.saveAll(
            findTargetByToken(projectCode, targetTokens)
                .map {
                    TargetAffiliation(
                        target = it,
                        group = findGroupByName(projectCode, groupName),
                    )
                }
        )

    private fun findTargetByToken(projectCode: String, targetTokens: List<String>) =
        targetRepository.findByProjectCodeAndTokenIn(projectCode, targetTokens)

    private fun findGroupByName(projectCode: String, groupName: String) =
        groupRepository.findByProjectCodeAndAndGroupName(projectCode, groupName)
            ?: throw GroupNotFoundException(projectCode, groupName)
}