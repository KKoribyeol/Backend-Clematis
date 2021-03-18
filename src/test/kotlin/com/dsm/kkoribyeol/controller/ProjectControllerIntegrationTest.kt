package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.ProjectCreationRequest
import com.dsm.kkoribyeol.controller.request.ProjectModificationRequest
import com.dsm.kkoribyeol.controller.response.ProjectCreationResponse
import com.dsm.kkoribyeol.exception.handler.CommonExceptionResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class ProjectControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `프로젝트 생성하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            ProjectCreationRequest(
                name = "project",
                description = "description",
            )
        )

        val responseBody = objectMapper.readValue<ProjectCreationResponse>(
            mock.perform(post("/project")
                .header("Authorization", "this-is-test-token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.projectId.substring(0, responseBody.projectId.length - 8)).isEqualTo("project")
    }

    @Test
    fun `프로젝트 생성하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            ProjectCreationRequest(
                name = "project",
                description = "description",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/project")
                .header("Authorization", "this-is-invalid-token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isUnauthorized)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("INVALID_TOKEN")
    }

    @Test
    fun `프로젝트 생성하기 - 400 ALREADY_EXIST_PROJECT`() {
        val requestBody = objectMapper.writeValueAsString(
            ProjectCreationRequest(
                name = "savedProject",
                description = "savedDescription",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/project")
                .header("Authorization", "this-is-test-token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("ALREADY_EXIST_PROJECT")
    }

    @Test
    fun `프로젝트 수정하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            ProjectModificationRequest(
                name = "newProject",
                description = "newDescription",
            )
        )

        mock.perform(patch("/project/savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `프로젝트 수정하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            ProjectModificationRequest(
                name = "newProject",
                description = "newDescription",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/project/savedProject-finally")
                .header("Authorization", "this-is-invalid-token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isUnauthorized)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("INVALID_TOKEN")
    }

    @Test
    fun `프로젝트 수정하기 - 404 PROJECT_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            ProjectModificationRequest(
                name = "newProject",
                description = "newDescription",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/project/nonExistProject-finally")
                .header("Authorization", "this-is-test-token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("PROJECT_NOT_FOUND")
    }
}