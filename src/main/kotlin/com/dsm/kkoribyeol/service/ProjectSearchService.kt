package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Project
import com.dsm.kkoribyeol.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.repository.ProjectRepository
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