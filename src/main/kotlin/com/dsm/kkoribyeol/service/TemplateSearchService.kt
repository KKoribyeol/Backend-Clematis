package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Template
import com.dsm.kkoribyeol.exception.TemplateNotFoundException
import com.dsm.kkoribyeol.repository.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TemplateSearchService(
    private val templateRepository: TemplateRepository,
) {

    fun search(templateId: Long) =
        templateRepository.findByIdOrNull(templateId) ?: throw TemplateNotFoundException(templateId)

    fun searchAll(): List<Template> = templateRepository.findAll()
}