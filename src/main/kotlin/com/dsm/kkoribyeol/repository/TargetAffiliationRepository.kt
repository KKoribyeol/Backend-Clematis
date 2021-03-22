package com.dsm.kkoribyeol.repository

import com.dsm.kkoribyeol.domain.TargetAffiliation
import org.springframework.data.jpa.repository.JpaRepository

interface TargetAffiliationRepository : JpaRepository<TargetAffiliation, Long> {
    fun findByGroupProjectCodeAndGroupGroupName(projectCode: String, groupName: String): List<TargetAffiliation>
    fun existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(projectCode: String, groupName: String, targetTokens: List<String>): Boolean
}