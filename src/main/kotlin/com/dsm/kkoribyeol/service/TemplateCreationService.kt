package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.repository.TemplateRepository
import org.springframework.stereotype.Service

@Service
class TemplateCreationService(
    val templateRepository: TemplateRepository,
) {

    fun create(title: String, body: String) {

    }
}