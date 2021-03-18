package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Project
import com.dsm.kkoribyeol.exception.AccountNotFoundException
import com.dsm.kkoribyeol.exception.AlreadyExistAccountException
import com.dsm.kkoribyeol.exception.AlreadyExistProjectException
import com.dsm.kkoribyeol.repository.AccountRepository
import com.dsm.kkoribyeol.repository.ProjectRepository
import com.dsm.kkoribyeol.service.provider.RandomProjectCodeProvider
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProjectCreationService(
    private val projectRepository: ProjectRepository,
    private val accountRepository: AccountRepository,
    private val randomProjectCodeProvider: RandomProjectCodeProvider,
) {

    fun createProject(accountId: String, projectName: String, projectDescription: String?) =
        if (isExistProject(projectName))
            throw AlreadyExistProjectException(projectName)
        else
            save(
                accountId = accountId,
                projectName = projectName,
                projectDescription = projectDescription,
            ).id

    private fun isExistProject(projectName: String) =
        projectRepository.existsByName(projectName)

    private fun save(accountId: String, projectName: String, projectDescription: String?) =
        projectRepository.save(
            Project(
                id = generateProjectId(projectName),
                name = projectName,
                description = projectDescription,
                owner = findProjectById(accountId),
            )
        )

    private fun generateProjectId(projectName: String) =
        "$projectName-${randomProjectCodeProvider.generateRandomCode()}"

    private fun findProjectById(accountId: String) =
        accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundException(accountId)
}