package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Template
import com.dsm.kkoribyeol.exception.AlreadyExistTemplateException
import com.dsm.kkoribyeol.repository.TemplateRepository
import org.springframework.stereotype.Service

@Service
class TemplateCreationService(
    val templateRepository: TemplateRepository,
) {

    fun create(templateTitle: String, templateBody: String) =
        if (!isDuplicatedTemplate(templateTitle, templateBody))
            saveTemplate(templateTitle, templateBody)
        else
            throw AlreadyExistTemplateException(templateTitle, templateBody)

    private fun isDuplicatedTemplate(templateTitle: String, templateBody: String) =
        templateRepository.existsByTitleAndBody(
            title = templateTitle,
            body = templateBody,
        )

    private fun saveTemplate(templateTitle: String, templateBody: String) =
        templateRepository.save(
            Template(
                title = templateTitle,
                body = templateBody,
            )
        )
}