package com.dsm.clematis.domain.template.controller

import com.dsm.clematis.domain.template.controller.request.TemplateRequest
import com.dsm.clematis.domain.template.controller.response.TemplateCreationResponse
import com.dsm.clematis.domain.template.controller.response.TemplateSearchAllResponse
import com.dsm.clematis.domain.template.controller.response.TemplateSearchAllResponse.TemplateSearchResponse
import com.dsm.clematis.domain.template.controller.response.TemplateSearchDetailResponse
import com.dsm.clematis.domain.template.exception.TemplateSearchException
import com.dsm.clematis.domain.template.service.TemplateCreationService
import com.dsm.clematis.domain.template.service.TemplateDeletionService
import com.dsm.clematis.domain.template.service.TemplateModificationService
import com.dsm.clematis.domain.template.service.TemplateSearchService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
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
        @Valid
        @RequestBody
        request: TemplateRequest,

        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,
    ) = TemplateCreationResponse(
        creationNumber = creationService.createTemplate(
            templateTitle = request.title,
            templateBody = request.body,
            projectCode = projectCode,
        )
    )

    @PatchMapping("/{templateId}")
    fun modifyTemplate(
        @NotNull(message = "<NULL>")
        @Positive(message = "<양수가 아님>")
        @PathVariable("templateId")
        templateId: Long,

        @RequestBody @Valid
        request: TemplateRequest,

        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,
    ) = modificationService.modifyTemplate(
        templateId = templateId,
        templateTitle = request.title,
        templateBody = request.body,
        projectCode = projectCode,
    )

    @DeleteMapping("/{templateId}")
    fun deleteTemplate(
        @NotNull(message = "<NULL>") @Positive(message = "<양수가 아님>")
        @PathVariable("templateId")
        templateId: Long,

        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,
    ) = deletionService.deleteTemplate(
        templateId = templateId,
        projectCode = projectCode,
    )

    @GetMapping
    fun searchTemplate(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,
    ) = TemplateSearchAllResponse(
            templates = searchService.searchAllTemplate(projectCode)
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
        @NotNull(message = "<NULL>") @Positive(message = "<양수가 아님>")
        @PathVariable("templateId")
        templateId: Long,

        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,
    ): TemplateSearchDetailResponse {
        val findTemplate = searchService.searchTemplate(
            templateId = templateId,
            projectCode = projectCode,
        )

        return TemplateSearchDetailResponse(
            templateId = findTemplate.id ?: throw TemplateSearchException(),
            templateTitle = findTemplate.title,
            templateBody = findTemplate.body,
            templateCreatedAt = findTemplate.createdAt,
            templateUpdatedAt = findTemplate.updatedAt,
        )
    }
}