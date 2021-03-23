package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.template.domain.Template
import com.dsm.clematis.domain.template.exception.AlreadyExistTemplateException
import com.dsm.clematis.domain.template.exception.TemplateCreationException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import org.springframework.stereotype.Service

@Service
class TemplateCreationService(
    val templateRepository: TemplateRepository,
) {

    fun createTemplate(templateTitle: String, templateBody: String) =
        if (!isDuplicateTemplate(templateTitle, templateBody))
            save(templateTitle, templateBody) ?: throw TemplateCreationException()
        else
            throw AlreadyExistTemplateException(templateTitle, templateBody)

    private fun isDuplicateTemplate(templateTitle: String, templateBody: String) =
        templateRepository.existsByTitleAndBody(
            title = templateTitle,
            body = templateBody,
        )

    private fun save(templateTitle: String, templateBody: String) =
        templateRepository.save(
            Template(
                title = templateTitle,
                body = templateBody,
            )
        ).id
}