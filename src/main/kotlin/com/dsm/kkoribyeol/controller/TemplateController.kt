package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.TemplateRequest
import com.dsm.kkoribyeol.controller.response.TemplateCreationResponse
import com.dsm.kkoribyeol.controller.response.TemplateListSearchResponse
import com.dsm.kkoribyeol.controller.response.TemplateListSearchResponse.TemplateSearchResponse
import com.dsm.kkoribyeol.exception.TemplateSearchException
import com.dsm.kkoribyeol.service.TemplateCreationService
import com.dsm.kkoribyeol.service.TemplateDeletionService
import com.dsm.kkoribyeol.service.TemplateModificationService
import com.dsm.kkoribyeol.service.TemplateSearchService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/template")
@Validated
class TemplateController(
    private val creationService: TemplateCreationService,
    private val modificationService: TemplateModificationService,
    private val deletionService: TemplateDeletionService,
    private val searchService: TemplateSearchService,
) {

    @PostMapping
    fun createTemplate(
        @RequestBody @Valid
        request: TemplateRequest,
    ) = TemplateCreationResponse(
        creationNumber = creationService.create(
            templateTitle = request.title,
            templateBody = request.body,
        )
    )

    @PatchMapping("/{templateId}")
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

    @DeleteMapping("/{templateId}")
    fun deleteTemplate(
        @PathVariable("templateId")
        @NotNull(message = "<NULL>") @Positive(message = "<양수가 아님>")
        templateId: Long,
    ) = deletionService.delete(
        templateId = templateId,
    )

    @GetMapping
    fun searchTemplate() =
        TemplateListSearchResponse(
            templates = searchService.searchAll()
                .map {
                    TemplateSearchResponse(
                        templateId = it.id ?: throw TemplateSearchException(),
                        templateTitle = it.title,
                        templateBody = it.body,
                    )
                }
        )

    @GetMapping("/{templateId}")
    fun searchTemplateDetail(
        @PathVariable("templateId")
        @NotNull(message = "<NULL>") @Positive(message = "<양수가 아님>")
        templateId: Long,
    ): TemplateSearchResponse {
        val findTemplate = searchService.search(templateId)
        return TemplateSearchResponse(
            templateId = findTemplate.id ?: throw TemplateSearchException(),
            templateTitle = findTemplate.title,
            templateBody = findTemplate.body,
        )
    }
}