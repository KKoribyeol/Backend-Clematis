package com.dsm.clematis.domain.affiliation.service

import com.dsm.clematis.domain.affiliation.repository.TargetAffiliationRepository
import org.springframework.stereotype.Service

@Service
class AffiliationSearchService(
    private val affiliationRepository: TargetAffiliationRepository,
) {

    fun getAffiliatedTarget(projectCode: String, groupNames: List<String>, targetTokens: List<String>) =
        affiliationRepository.findByGroupProjectCodeAndGroupGroupNameIn(
            projectCode = projectCode,
            groupNames = groupNames,
        ).map {
            it.target.token
        }.plus(targetTokens).distinct()
}