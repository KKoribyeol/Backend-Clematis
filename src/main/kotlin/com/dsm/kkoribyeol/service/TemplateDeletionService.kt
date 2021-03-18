package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.TemplateNotFoundException
import com.dsm.kkoribyeol.repository.TemplateRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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