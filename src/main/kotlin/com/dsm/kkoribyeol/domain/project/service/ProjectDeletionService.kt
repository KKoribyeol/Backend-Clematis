package com.dsm.kkoribyeol.domain.project.service

import com.dsm.kkoribyeol.domain.project.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.domain.project.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectDeletionService(
    private val projectRepository: ProjectRepository,
) {

    fun deleteProject(projectCode: String) =
        if (isExistProject(projectCode))
            projectRepository.deleteById(projectCode)
        else
            throw ProjectNotFoundException(projectCode)

    private fun isExistProject(projectCode: String) =
        projectRepository.existsById(projectCode)
}