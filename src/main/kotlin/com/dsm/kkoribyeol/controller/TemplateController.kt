package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.TemplateRequest
import com.dsm.kkoribyeol.controller.response.TemplateCreationResponse
import com.dsm.kkoribyeol.exception.TemplateCreationException
import com.dsm.kkoribyeol.service.TemplateCreationService
import com.dsm.kkoribyeol.service.TemplateDeletionService
import com.dsm.kkoribyeol.service.TemplateModificationService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@RestController
@Validated
class TemplateController(
    private val creationService: TemplateCreationService,
    private val modificationService: TemplateModificationService,
    private val deletionService: TemplateDeletionService,
) {

    @PostMapping("/template")
    fun createTemplate(
        @RequestBody @Valid
        request: TemplateRequest,
    ) = TemplateCreationResponse(
        creationNumber = creationService.create(
            templateTitle = request.title,
            templateBody = request.body,
        ) ?: throw TemplateCreationException()
    )

    @PatchMapping("/template/{templateId}")
    fun modifyTemplate(
        @PathVariable("templateId")
        @NotNull(message = "<NULL>") @Positive(message = "<양수가 아님>")
        templateId: Long,
        @RequestBody @Valid
        request: TemplateRequest,
    ) = modificationService.modify(
        templateId = templateId,
        templateTitle = request.title,
        templateBody = request.body,
    )

    @DeleteMapping("/template/{templateId}")
    fun deleteTemplate(
        @PathVariable("templateId")
        @NotNull(message = "<NULL>") @Positive(message = "<양수가 아님>")
        templateId: Long,
    ) = deletionService.delete(
        templateId = templateId,
    )
}