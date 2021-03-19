package com.dsm.kkoribyeol.repository

import com.dsm.kkoribyeol.domain.Target
import org.springframework.data.jpa.repository.JpaRepository

interface TargetRepository : JpaRepository<Target, String> {
    fun findByProjectCodeAndToken(code: String, token: String): Target?
    fun deleteByProjectCodeAndTokenIn(code: String, tokens: List<String>)
    fun existsByProjectCodeAndTokenIn(code: String, tokens: List<String>): Boolean
}