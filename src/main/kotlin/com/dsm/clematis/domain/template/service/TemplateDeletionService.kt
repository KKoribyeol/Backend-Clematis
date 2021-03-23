package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.template.exception.TemplateNotFoundException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import org.springframework.stereotype.Service

@Service
class TemplateDeletionService(
    private val templateRepository: TemplateRepository,
) {

    fun deleteTemplate(templateId: Long) =
        if (isExistTemplate(templateId))
            templateRepository.deleteById(templateId)
        else
            throw TemplateNotFoundException(templateId)

    private fun isExistTemplate(templateId: Long) =
        templateRepository.existsById(templateId)
}