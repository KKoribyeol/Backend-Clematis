package com.dsm.clematis.domain.template.repository

import com.dsm.clematis.domain.template.domain.Template
import org.springframework.data.jpa.repository.JpaRepository

interface TemplateRepository : JpaRepository<Template, Long> {
    fun findByProjectCode(projectCode: String): List<Template>
    fun findByIdAndProjectCode(id: Long, projectCode: String): Template?

    fun existsByIdAndProjectCode(id: Long, projectCode: String): Boolean
    fun existsByTitleAndBodyAndProjectCode(title: String, body: String, projectCode: String): Boolean

    fun deleteByIdAndProjectCode(id: Long, projectCode: String)
}