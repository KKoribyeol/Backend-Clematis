package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.TargetAffiliation
import com.dsm.kkoribyeol.exception.AlreadyExistAffiliationException
import com.dsm.kkoribyeol.exception.GroupNotFoundException
import com.dsm.kkoribyeol.repository.TargetAffiliationRepository
import com.dsm.kkoribyeol.repository.TargetGroupRepository
import com.dsm.kkoribyeol.repository.TargetRepository
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