package com.dsm.clematis.domain.target.repository

import com.dsm.clematis.domain.target.domain.Target
import org.springframework.data.jpa.repository.JpaRepository

interface TargetRepository : JpaRepository<Target, String> {
    fun findByProjectCodeAndToken(code: String, token: String): Target?
    fun findByProjectCodeAndTokenIn(code: String, tokens: List<String>): List<Target>
    fun findByProjectCode(code: String): List<Target>

    fun deleteByProjectCodeAndTokenIn(code: String, tokens: List<String>)
    fun deleteByProjectCodeAndToken(code: String, token: String)

    fun existsByProjectCodeAndTokenIn(code: String, tokens: List<String>): Boolean
    fun existsByProjectCodeAndNicknameIn(code: String, nicknames: List<String>): Boolean
}