package com.dsm.clematis.domain.template.repository

import com.dsm.clematis.domain.template.domain.Template
import org.springframework.data.jpa.repository.JpaRepository

interface TemplateRepository : JpaRepository<Template, Long> {
    fun existsByTitleAndBody(title: String, body: String): Boolean
}