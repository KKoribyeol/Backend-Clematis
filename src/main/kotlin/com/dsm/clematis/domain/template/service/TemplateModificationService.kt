package com.dsm.clematis.domain.template.service

import com.dsm.clematis.domain.template.exception.TemplateNotFoundException
import com.dsm.clematis.domain.template.repository.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TemplateModificationService(
    private val templateRepository: TemplateRepository,
) {

    fun modifyTemplate(templateId: Long, templateTitle: String, templateBody: String, projectCode: String) =
        findTemplateById(templateId, projectCode)
            .modifyContent(templateTitle, templateBody)

    private fun findTemplateById(templateId: Long, projectCode: String) =
        templateRepository.findByIdAndProjectCode(templateId, projectCode)
            ?: throw TemplateNotFoundException(templateId)
}