package com.dsm.clematis.domain.group.repository

import com.dsm.clematis.domain.group.domain.TargetGroup
import org.springframework.data.jpa.repository.JpaRepository

interface TargetGroupRepository : JpaRepository<TargetGroup, Long> {
    fun existsByProjectCodeAndGroupName(projectCode: String, groupName: String): Boolean

    fun findByProjectCodeAndAndGroupName(projectCode: String, groupName: String): TargetGroup?
    fun findByProjectCode(projectCode: String): List<TargetGroup>

    fun deleteByProjectCodeAndAndGroupName(projectCode: String, groupName: String)
}