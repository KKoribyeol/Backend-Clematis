package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.NonExistTemplateException
import com.dsm.kkoribyeol.repository.TemplateRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TemplateModificationService(
    private val templateRepository: TemplateRepository,
) {

    fun modify(templateId: Long, templateTitle: String, templateBody: String) =
        findTemplateById(templateId).modifyContent(templateTitle, templateBody)

    private fun findTemplateById(templateId: Long) =
        templateRepository.findByIdOrNull(templateId)?: throw NonExistTemplateException(templateId)
}