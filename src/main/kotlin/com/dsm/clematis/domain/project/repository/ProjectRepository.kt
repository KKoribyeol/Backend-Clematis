package com.dsm.clematis.domain.project.repository

import com.dsm.clematis.domain.project.domain.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, String> {
    fun existsByName(name: String): Boolean
}