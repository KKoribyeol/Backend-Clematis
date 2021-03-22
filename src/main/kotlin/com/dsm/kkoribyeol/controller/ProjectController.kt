package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.ProjectCreationRequest
import com.dsm.kkoribyeol.controller.request.ProjectModificationRequest
import com.dsm.kkoribyeol.controller.response.ProjectCreationResponse
import com.dsm.kkoribyeol.controller.response.ProjectSearchAllResponse
import com.dsm.kkoribyeol.controller.response.ProjectSearchAllResponse.ProjectSearchResponse
import com.dsm.kkoribyeol.controller.response.ProjectSearchDetailResponse
import com.dsm.kkoribyeol.service.ProjectCreationService
import com.dsm.kkoribyeol.service.ProjectDeletionService
import com.dsm.kkoribyeol.service.ProjectModificationService
import com.dsm.kkoribyeol.service.ProjectSearchService
import com.dsm.kkoribyeol.service.provider.AuthenticationProvider
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@RestController
@RequestMapping("/project")
@Validated
class ProjectController(
    private val creationService: ProjectCreationService,
    private val modificationService: ProjectModificationService,
    private val deletionService: ProjectDeletionService,
    private val searchService: ProjectSearchService,
    private val authenticationProvider: AuthenticationProvider,
) {

    @PostMapping
    fun createProject(
        @Valid
        @RequestBody
        creationRequest: ProjectCreationRequest,

    ) = ProjectCreationResponse(
        projectId = creationService.createProject(
            accountId = authenticationProvider.getAccountIdByAuthentication(),
            projectName = creationRequest.name,
            projectDescription = creationRequest.description,
        ),
    )

    @PatchMapping("/{projectCode}")
    fun modifyProject(
        @Valid
        @RequestBody
        request: ProjectModificationRequest,

        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @PathVariable("projectCode")
        projectCode: String,

    ) = modificationService.modifyProject(
        projectCode = projectCode,
        newProjectName = request.name,
        newProjectDescription = request.description,
    )

    @DeleteMapping("/{projectCode}")
    fun deleteProject(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @PathVariable("projectCode")
        projectCode: String,

    ) = deletionService.deleteProject(
        projectCode = projectCode,
    )

    @GetMapping
    fun searchProject() =
        ProjectSearchAllResponse(
            projects = searchService.searchAllProject()
                .map {
                    ProjectSearchResponse(
                        projectCode = it.code,
                        projectName = it.name,
                    )
                }
        )

    @GetMapping("/{projectCode}")
    fun searchProjectDetail(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @PathVariable("projectCode")
        projectCode: String,

    ): ProjectSearchDetailResponse {
        val findProject = searchService.searchProject(projectCode)
        return ProjectSearchDetailResponse(
            projectCode = findProject.code,
            projectName = findProject.name,
            projectDescription = findProject.description,
        )
    }
}