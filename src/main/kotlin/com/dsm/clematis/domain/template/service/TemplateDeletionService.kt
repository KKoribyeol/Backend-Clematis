package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.template.exception.TemplateNotFoundException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TemplateDeletionService(
    private val templateRepository: TemplateRepository,
) {

    fun deleteTemplate(templateId: Long, projectCode: String) =
        if (isExistTemplate(templateId, projectCode))
            templateRepository.deleteByIdAndProjectCode(templateId, projectCode)
        else
            throw TemplateNotFoundException(templateId)

    private fun isExistTemplate(templateId: Long, projectCode: String) =
        templateRepository.existsByIdAndProjectCode(templateId, projectCode)
}