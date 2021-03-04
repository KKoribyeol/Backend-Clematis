package com.dsm.kkoribyeol.repository

import com.dsm.kkoribyeol.domain.Template
import org.springframework.data.jpa.repository.JpaRepository

interface TemplateRepository : JpaRepository<Template, Long>