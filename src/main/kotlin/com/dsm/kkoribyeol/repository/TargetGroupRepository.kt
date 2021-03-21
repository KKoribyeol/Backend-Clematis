package com.dsm.kkoribyeol.repository

import com.dsm.kkoribyeol.domain.TargetGroup
import org.springframework.data.jpa.repository.JpaRepository

interface TargetGroupRepository : JpaRepository<TargetGroup, Long> {
    fun existsByProjectCodeAndGroupName(projectCode: String, groupName: String): Boolean
    fun findByProjectCodeAndAndGroupName(projectCode: String, groupName: String): TargetGroup?
}