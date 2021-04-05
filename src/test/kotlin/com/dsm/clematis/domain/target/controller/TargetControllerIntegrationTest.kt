package com.dsm.clematis.domain.target.controller

import com.dsm.clematis.domain.target.controller.request.TargetModificationRequest
import com.dsm.clematis.domain.target.controller.request.TargetRegistrationAllRequest
import com.dsm.clematis.domain.target.controller.request.TargetRegistrationAllRequest.TargetRegistrationRequest
import com.dsm.clematis.domain.target.controller.request.TargetUnregisterRequest
import com.dsm.clematis.domain.target.controller.response.TargetSearchAllResponse.TargetSearchResponse
import com.dsm.clematis.domain.target.controller.response.TargetSearchAllResponse
import com.dsm.clematis.global.exception.response.CommonExceptionResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class TargetControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `타겟 등록하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetRegistrationAllRequest(
                targets = listOf(
                    TargetRegistrationRequest(
                        targetToken = "nonExistToken",
                        targetNickname = "nonExistNickname",
                        targetName = "nonExistName",
                    )
                )
            )
        )

        mock.perform(post("/target")
            .header("Authorization", "this-is-test-token")
            .header("projectCode", "savedProject-finally")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `타겟 등록하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetRegistrationAllRequest(
                targets = listOf(
                    TargetRegistrationRequest(
                        targetToken = "nonExistToken",
                        targetNickname = "nonExistNickname",
                        targetName = "nonExistName",
                    )
                )
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/target")
                .header("Authorization", "this-is-invalid-token")
                .header("projectCode", "savedProject-finally")
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
    fun `타겟 등록하기 - 400 ALREADY_EXIST_TARGET`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetRegistrationAllRequest(
                targets = listOf(
                    TargetRegistrationRequest(
                        targetToken = "savedToken",
                        targetNickname = "savedNickname",
                        targetName = "savedName",
                    )
                )
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/target")
                .header("Authorization", "this-is-test-token")
                .header("projectCode", "savedProject-finally")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("ALREADY_EXIST_TARGET")
    }

    @Test
    fun `타겟 등록하기 - 404 PROJECT_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetRegistrationAllRequest(
                targets = listOf(
                    TargetRegistrationRequest(
                        targetToken = "nonExistToken",
                        targetNickname = "nonExistNickname",
                        targetName = "nonExistName",
                    )
                )
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/target")
                .header("Authorization", "this-is-test-token")
                .header("projectCode", "nonExistProject-finally")
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

    @Test
    fun `타겟 정보 변경하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetModificationRequest(
                nickname = null,
                name = null,
            )
        )

        mock.perform(patch("/target/savedToken")
            .header("Authorization", "this-is-test-token")
            .header("projectCode", "savedProject-finally")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `타겟 정보 변경하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetModificationRequest(
                nickname = null,
                name = null,
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/target/savedToken")
                .header("Authorization", "this-is-invalid-token")
                .header("projectCode", "savedProject-finally")
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
    fun `타겟 정보 변경하기 - 404 TARGET_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetModificationRequest(
                nickname = null,
                name = null,
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/target/nonExistToken")
                .header("Authorization", "this-is-test-token")
                .header("projectCode", "savedProject-finally")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("TARGET_NOT_FOUND")
    }

    @Test
    fun `타겟 삭제하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetUnregisterRequest(
                tokens = listOf("savedToken")
            )
        )

        mock.perform(delete("/target")
            .header("Authorization", "this-is-test-token")
            .header("projectCode", "savedProject-finally")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `타겟 삭제하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetUnregisterRequest(
                tokens = listOf("savedToken")
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/target/savedToken")
                .header("Authorization", "this-is-invalid-token")
                .header("projectCode", "savedProject-finally")
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
    fun `타겟 단일 삭제하기 - 200`() {
        mock.perform(delete("/target/savedToken")
            .header("Authorization", "this-is-test-token")
            .header("projectCode", "savedProject-finally")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `타겟 단일 삭제하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/target/savedToken")
                .header("Authorization", "this-is-invalid-token")
                .header("projectCode", "savedProject-finally")
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
    fun `타겟 단일 조회하기 - 200`() {
        val responseBody = objectMapper.readValue<TargetSearchResponse>(
            mock.perform(get("/target/savedToken")
                .header("Authorization", "this-is-test-token")
                .header("projectCode", "savedProject-finally")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.token).isEqualTo("savedToken")
        assertThat(responseBody.nickname).isEqualTo("savedNickname")
        assertThat(responseBody.name).isEqualTo("savedName")
    }

    @Test
    fun `타겟 단일 조회하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/target/savedToken")
                .header("Authorization", "this-is-invalid-token")
                .header("projectCode", "savedProject-finally")
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
    fun `타겟 단일 조회하기 - 404 TARGET_NOT_FOUND`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/target/nonExistToken")
                .header("Authorization", "this-is-test-token")
                .header("projectCode", "savedProject-finally")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("TARGET_NOT_FOUND")
    }

    @Test
    fun `타겟 다중 조회하기 - 200`() {
        val responseBody = objectMapper.readValue<TargetSearchAllResponse>(
            mock.perform(get("/target")
                .header("Authorization", "this-is-test-token")
                .header("projectCode", "savedProject-finally")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.targets).containsAll(
            listOf(
                TargetSearchResponse(
                    token = "savedToken",
                    nickname = "savedNickname",
                    name = "savedName",
                )
            )
        )
    }

    @Test
    fun `타겟 다중 조회하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/target")
                .header("Authorization", "this-is-invalid-token")
                .header("projectCode", "savedProject-finally")
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
}