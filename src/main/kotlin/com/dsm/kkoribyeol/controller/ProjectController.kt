package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.ProjectRequest
import com.dsm.kkoribyeol.controller.response.ProjectCreationResponse
import com.dsm.kkoribyeol.service.ProjectCreationService
import com.dsm.kkoribyeol.service.provider.AuthenticationProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/project")
class ProjectController(
    private val creationService: ProjectCreationService,
    private val authenticationProvider: AuthenticationProvider,
) {

    @PostMapping
    fun createProject(
        @RequestBody @Valid
        request: ProjectRequest,
    ) = ProjectCreationResponse(
        projectId = creationService.createProject(
            accountId = authenticationProvider.getAccountIdByAuthentication(),
            projectName = request.name,
            projectDescription = request.description,
        ),
    )
}