package com.dsm.clematis.domain.affiliation.controller

import com.dsm.clematis.domain.affiliation.controller.request.TargetOfGroupingRequest
import com.dsm.clematis.domain.affiliation.controller.request.TargetOfUngroupingRequest
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class AffiliationControllerTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `타겟을 그룹에 소속시키기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetOfGroupingRequest(
                groupName = "savedName",
                targetToken = "unaffiliatedToken",
            )
        )

        mock.perform(post("/affiliation")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `타겟을 그룹에 소속시키기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetOfGroupingRequest(
                groupName = "savedName",
                targetToken = "unaffiliatedToken",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/affiliation")
                .header("projectCode", "savedProject-finally")
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
    fun `타겟을 그룹에 소속시키기 - 400 ALREADY_EXIST_AFFILIATION`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetOfGroupingRequest(
                groupName = "savedName",
                targetToken = "savedToken",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/affiliation")
                .header("projectCode", "savedProject-finally")
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

        assertThat(responseBody.code).isEqualTo("ALREADY_EXIST_AFFILIATION")
    }

    @Test
    fun `타겟을 그룹에 소속시키기 - 404 GROUP_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetOfGroupingRequest(
                groupName = "nonExistName",
                targetToken = "unaffiliatedToken",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/affiliation")
                .header("projectCode", "savedProject-finally")
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

        assertThat(responseBody.code).isEqualTo("GROUP_NOT_FOUND")
    }

    @Test
    fun `타겟을 그룹에서 해제하기 - 200`() {
        mock.perform(delete("/affiliation?group-name=savedName&target-token=savedToken")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `타겟을 그룹에서 해제하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            TargetOfUngroupingRequest(
                groupName = "savedName",
                targetToken = "savedToken",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/affiliation")
                .header("projectCode", "savedProject-finally")
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
    fun `타겟을 그룹에서 해제하기 - 404 AFFILIATION_NOT_FOUND`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/affiliation?group-name=nonExistName&target-token=nonExistToken")
                .header("projectCode", "savedProject-finally")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("AFFILIATION_NOT_FOUND")
    }
}