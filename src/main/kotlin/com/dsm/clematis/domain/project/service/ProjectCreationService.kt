package com.dsm.clematis.domain.project.service

import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.account.exception.AccountNotFoundException
import com.dsm.clematis.domain.project.exception.AlreadyExistProjectException
import com.dsm.clematis.domain.account.repository.AccountRepository
import com.dsm.clematis.domain.project.repository.ProjectRepository
import com.dsm.clematis.domain.project.service.provider.RandomProjectCodeProvider
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
            ).code

    private fun isExistProject(projectName: String) =
        projectRepository.existsByName(projectName)

    private fun save(accountId: String, projectName: String, projectDescription: String?) =
        projectRepository.save(
            Project(
                code = generateProjectCode(projectName),
                name = projectName,
                description = projectDescription,
                owner = findProjectById(accountId),
            )
        )

    private fun generateProjectCode(projectName: String) =
        "$projectName-${randomProjectCodeProvider.generateRandomCode()}"

    private fun findProjectById(accountId: String) =
        accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundException(accountId)
}