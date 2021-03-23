package com.dsm.kkoribyeol.domain.target.repository

import com.dsm.kkoribyeol.domain.target.domain.Target
import org.springframework.data.jpa.repository.JpaRepository

interface TargetRepository : JpaRepository<Target, String> {
    fun findByProjectCodeAndToken(code: String, token: String): Target?
    fun findByProjectCodeAndTokenIn(code: String, tokens: List<String>): List<Target>
    fun deleteByProjectCodeAndTokenIn(code: String, tokens: List<String>)
    fun existsByProjectCodeAndTokenIn(code: String, tokens: List<String>): Boolean
    fun existsByProjectCodeAndNicknameIn(code: String, nicknames: List<String>): Boolean
}