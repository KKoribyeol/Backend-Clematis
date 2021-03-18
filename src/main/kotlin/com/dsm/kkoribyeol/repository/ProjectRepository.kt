package com.dsm.kkoribyeol.repository

import com.dsm.kkoribyeol.domain.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, String> {
    fun existsByName(name: String): Boolean
}