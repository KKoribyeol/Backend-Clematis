package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.controller.request.TargetRegistrationAllRequest.TargetRegistrationRequest
import com.dsm.kkoribyeol.domain.Target
import com.dsm.kkoribyeol.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.repository.ProjectRepository
import com.dsm.kkoribyeol.repository.TargetRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TargetRegistrationService(
    private val targetRepository: TargetRepository,
    private val projectRepository: ProjectRepository,
) {

    fun registerTarget(projectCode: String, targets: List<TargetRegistrationRequest>) =
        save(projectCode, targets)

    private fun save(projectCode: String, targets: List<TargetRegistrationRequest>) {
        targetRepository.saveAll(
            targets.map {
                Target(
                    token = it.targetToken,
                    nickname = it.targetNickname,
                    name = it.targetName,
                    project = findProjectByCode(projectCode),
                )
            }
        )
    }

    fun unregisterTarget(projectCode: String, targets: List<String>) =
        targetRepository.deleteByProjectCodeAndTokenIn(
            code = projectCode,
            tokens = targets,
        )

    private fun findProjectByCode(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode) ?: throw ProjectNotFoundException(projectCode)
}