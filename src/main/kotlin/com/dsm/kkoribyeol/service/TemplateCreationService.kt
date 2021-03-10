package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Template
import com.dsm.kkoribyeol.exception.AlreadyExistTemplateException
import com.dsm.kkoribyeol.exception.TemplateCreationException
import com.dsm.kkoribyeol.repository.TemplateRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TemplateCreationService(
    val templateRepository: TemplateRepository,
) {

    fun create(templateTitle: String, templateBody: String) =
        if (!isDuplicated(templateTitle, templateBody))
            save(templateTitle, templateBody) ?: throw TemplateCreationException()
        else
            throw AlreadyExistTemplateException(templateTitle, templateBody)

    private fun isDuplicated(templateTitle: String, templateBody: String) =
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