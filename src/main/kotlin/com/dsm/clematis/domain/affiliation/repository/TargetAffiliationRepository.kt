package com.dsm.clematis.domain.affiliation.repository

import com.dsm.clematis.domain.affiliation.domain.TargetAffiliation
import org.springframework.data.jpa.repository.JpaRepository

interface TargetAffiliationRepository : JpaRepository<TargetAffiliation, Long> {
    fun existsByGroupProjectCodeAndGroupGroupNameAndTargetTokenIn(projectCode: String, groupName: String, targetTokens: List<String>): Boolean
    fun existsByGroupProjectCodeAndGroupGroupNameAndTargetToken(projectCode: String, groupName: String, targetToken: String): Boolean

    fun findByGroupProjectCodeAndGroupGroupName(projectCode: String, groupName: String): List<TargetAffiliation>
    fun findByGroupProjectCodeAndGroupGroupNameIn(projectCode: String, groupNames: List<String>): List<TargetAffiliation>

    fun deleteByGroupProjectCodeAndGroupGroupNameAndTargetToken(projectCode: String, groupName: String, targetToken: String)
}