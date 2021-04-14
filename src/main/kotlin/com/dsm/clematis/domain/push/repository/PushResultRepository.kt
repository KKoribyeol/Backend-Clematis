package com.dsm.clematis.domain.push.repository

import com.dsm.clematis.domain.push.domain.PushResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PushResultRepository : JpaRepository<PushResult, Int> {
    fun findByHistoryId(historyId: Long, pageable: Pageable): Page<PushResult>
}