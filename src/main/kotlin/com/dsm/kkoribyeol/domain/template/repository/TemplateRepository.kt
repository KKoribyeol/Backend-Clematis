package com.dsm.kkoribyeol.domain.template.repository

import com.dsm.kkoribyeol.domain.template.domain.Template
import org.springframework.data.jpa.repository.JpaRepository

interface TemplateRepository : JpaRepository<Template, Long> {
    fun existsByTitleAndBody(title: String, body: String): Boolean
}