package com.dsm.kkoribyeol.domain.project.service

import com.dsm.kkoribyeol.domain.project.domain.Project
import com.dsm.kkoribyeol.domain.project.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.domain.project.repository.ProjectRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProjectSearchService(
    private val projectRepository: ProjectRepository,
) {

    fun searchProject(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode) ?: throw ProjectNotFoundException(projectCode)

    fun searchAllProject(): List<Project> = projectRepository.findAll()
}