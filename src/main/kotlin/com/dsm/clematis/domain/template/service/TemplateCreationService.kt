package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.project.exception.ProjectNotFoundException
import com.dsm.clematis.domain.project.repository.ProjectRepository
import com.dsm.clematis.domain.template.domain.Template
import com.dsm.clematis.domain.template.exception.AlreadyExistTemplateException
import com.dsm.clematis.domain.template.exception.TemplateCreationException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TemplateCreationService(
    val templateRepository: TemplateRepository,
    val projectRepository: ProjectRepository,
) {

    fun createTemplate(templateTitle: String, templateBody: String, projectCode: String) =
        if (!isDuplicateTemplate(templateTitle, templateBody, projectCode))
            save(templateTitle, templateBody, projectCode)
                ?: throw TemplateCreationException()
        else
            throw AlreadyExistTemplateException(templateTitle, templateBody)

    private fun isDuplicateTemplate(templateTitle: String, templateBody: String, projectCode: String) =
        templateRepository.existsByTitleAndBodyAndProjectCode(
            title = templateTitle,
            body = templateBody,
            projectCode = projectCode,
        )

    private fun save(templateTitle: String, templateBody: String, projectCode: String) =
        templateRepository.save(
            Template(
                title = templateTitle,
                body = templateBody,
                project = getProjectByCode(projectCode),
            )
        ).id

    private fun getProjectByCode(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode)
            ?: throw ProjectNotFoundException(projectCode)
}