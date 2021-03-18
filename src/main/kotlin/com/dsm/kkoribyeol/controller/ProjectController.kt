package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.ProjectCreationRequest
import com.dsm.kkoribyeol.controller.request.ProjectModificationRequest
import com.dsm.kkoribyeol.controller.response.ProjectCreationResponse
import com.dsm.kkoribyeol.service.ProjectCreationService
import com.dsm.kkoribyeol.service.ProjectModificationService
import com.dsm.kkoribyeol.service.provider.AuthenticationProvider
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/project")
class ProjectController(
    private val creationService: ProjectCreationService,
    private val modificationService: ProjectModificationService,
    private val authenticationProvider: AuthenticationProvider,
) {

    @PostMapping
    fun createProject(
        @RequestBody @Valid
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
        @RequestBody @Valid
        request: ProjectModificationRequest,
        @PathVariable("projectCode")
        projectCode: String,
    ) = modificationService.modifyProject(
        projectCode = projectCode,
        newProjectName = request.name,
        newProjectDescription = request.description,
    )
}