package com.dsm.kkoribyeol.domain.template.service

import com.dsm.kkoribyeol.domain.template.domain.Template
import com.dsm.kkoribyeol.domain.template.exception.TemplateNotFoundException
import com.dsm.kkoribyeol.domain.template.repository.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TemplateSearchService(
    private val templateRepository: TemplateRepository,
) {

    fun searchTemplate(templateId: Long) =
        templateRepository.findByIdOrNull(templateId) ?: throw TemplateNotFoundException(templateId)

    fun searchAllTemplate(): List<Template> = templateRepository.findAll()
}