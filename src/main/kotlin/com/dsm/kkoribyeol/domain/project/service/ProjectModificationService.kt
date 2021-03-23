package com.dsm.kkoribyeol.domain.project.service

import com.dsm.kkoribyeol.domain.project.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.domain.project.repository.ProjectRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProjectModificationService(
    private val projectRepository: ProjectRepository,
) {

    fun modifyProject(projectCode: String, newProjectName: String?, newProjectDescription: String?) =
        findProjectByCode(projectCode).modifyContent(
            name = newProjectName,
            description = newProjectDescription,
        )

    private fun findProjectByCode(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode) ?: throw ProjectNotFoundException(projectCode)
}