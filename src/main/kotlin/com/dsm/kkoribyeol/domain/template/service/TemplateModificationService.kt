package com.dsm.kkoribyeol.domain.template.service

import com.dsm.kkoribyeol.domain.template.exception.TemplateNotFoundException
import com.dsm.kkoribyeol.domain.template.repository.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TemplateModificationService(
    private val templateRepository: TemplateRepository,
) {

    fun modifyTemplate(templateId: Long, templateTitle: String, templateBody: String) =
        findTemplateById(templateId).modifyContent(templateTitle, templateBody)

    private fun findTemplateById(templateId: Long) =
        templateRepository.findByIdOrNull(templateId)?: throw TemplateNotFoundException(templateId)
}