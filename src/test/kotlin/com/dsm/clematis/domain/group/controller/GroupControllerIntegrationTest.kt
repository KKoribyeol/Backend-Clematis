package com.dsm.clematis.domain.group.controller

import com.dsm.clematis.domain.group.controller.request.GroupRequest
import com.dsm.clematis.domain.group.controller.response.MultipleGroupResponse
import com.dsm.clematis.domain.group.controller.response.SingleGroupResponse
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
internal class GroupControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `그룹 생성하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            GroupRequest(
                groupName = "nonExistName",
            )
        )

        mock.perform(post("/group")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `그룹 생성하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            GroupRequest(
                groupName = "nonExistName",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/group")
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
    fun `그룹 생성하기 - 400 ALREADY_EXIST_GROUP`() {
        val requestBody = objectMapper.writeValueAsString(
            GroupRequest(
                groupName = "savedName",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/group")
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

        assertThat(responseBody.code).isEqualTo("ALREADY_EXIST_GROUP")
    }

    @Test
    fun `그룹 생성하기 - 404 PROJECT_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            GroupRequest(
                groupName = "nonExistName",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/group")
                .header("projectCode", "nonExistProject-finally")
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

    @Test
    fun `그룹 이름 변경하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            GroupRequest(
                groupName = "newName",
            )
        )

        mock.perform(patch("/group/savedName")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `그룹 이름 변경하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            GroupRequest(
                groupName = "newName",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/group/savedName")
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
    fun `그룹 이름 변경하기 - 404 GROUP_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            GroupRequest(
                groupName = "newName",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/group/nonExistName")
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
    fun `그룹 삭제하기 - 200`() {
        mock.perform(delete("/group/savedName")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `그룹 삭제하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/group/savedName")
                .header("projectCode", "savedProject-finally")
                .header("Authorization", "this-is-invalid-token")
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
    fun `그룹 삭제하기 - 404 GROUP_NOT_FOUND`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/group/nonExistName")
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

        assertThat(responseBody.code).isEqualTo("GROUP_NOT_FOUND")
    }

    @Test
    fun `그룹 단일 조회하기 - 200`() {
        val responseBody = objectMapper.readValue<SingleGroupResponse>(
            mock.perform(get("/group/savedName")
                .header("projectCode", "savedProject-finally")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.groupName).isEqualTo("savedName")
        assertThat(responseBody.targets).map<String> { it.token }.isEqualTo(listOf("savedToken"))
        assertThat(responseBody.targets).map<String> { it.nickname }.isEqualTo(listOf("savedNickname"))
    }

    @Test
    fun `그룹 단일 조회하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/group/savedName")
                .header("projectCode", "savedProject-finally")
                .header("Authorization", "this-is-invalid-token")
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
    fun `그룹 단일 조회하기 - 404 GROUP_NOT_FOUND`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/group/nonExistName")
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

        assertThat(responseBody.code).isEqualTo("GROUP_NOT_FOUND")
    }

    @Test
    fun `그룹 대량 조회하기 - 200`() {
        val responseBody = objectMapper.readValue<MultipleGroupResponse>(
            mock.perform(get("/group")
                .header("projectCode", "savedProject-finally")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.groups).map<String> { it.name }.containsAll(listOf("savedName"))
    }

    @Test
    fun `그룹 대량 조회하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/group")
                .header("projectCode", "savedProject-finally")
                .header("Authorization", "this-is-invalid-token")
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