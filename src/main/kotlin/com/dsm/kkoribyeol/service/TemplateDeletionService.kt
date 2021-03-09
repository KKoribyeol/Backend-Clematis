package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.NonExistTemplateException
import com.dsm.kkoribyeol.repository.TemplateRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TemplateDeletionService(
    private val templateRepository: TemplateRepository,
) {

    fun delete(templateId: Long) =
        if (isExist(templateId))
            templateRepository.deleteById(templateId)
        else
            throw NonExistTemplateException(templateId)

    private fun isExist(templateId: Long) =
        templateRepository.existsById(templateId)
}