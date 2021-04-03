package com.dsm.clematis.domain.project.service

import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.project.exception.ProjectNotFoundException
import com.dsm.clematis.domain.project.repository.ProjectRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProjectSearchService(
    private val projectRepository: ProjectRepository,
) {

    fun searchProject(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode) ?: throw ProjectNotFoundException(projectCode)

    fun searchAllProject(): List<Project> =
        projectRepository.findAll()
}