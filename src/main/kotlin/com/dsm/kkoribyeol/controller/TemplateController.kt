package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.TemplateRequest
import com.dsm.kkoribyeol.service.TemplateCreationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TemplateController(
    val templateCreationService: TemplateCreationService,
) {

    @PostMapping("/template")
    fun createTemplate(request: TemplateRequest) =
        templateCreationService.create(
            title = request.title,
            body = request.body,
        )
}