package com.dsm.kkoribyeol.domain.template.controller

import com.dsm.kkoribyeol.domain.template.controller.request.TemplateRequest
import com.dsm.kkoribyeol.domain.template.controller.response.TemplateCreationResponse
import com.dsm.kkoribyeol.domain.template.controller.response.TemplateSearchAllResponse
import com.dsm.kkoribyeol.domain.template.controller.response.TemplateSearchDetailResponse
import com.dsm.kkoribyeol.global.exception.response.CommonExceptionResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.*

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class TemplateControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `템플릿 생성하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplateRequest(
                title = "nonExistTitle",
                body = "nonExistBody",
            )
        )

        val responseBody = objectMapper.readValue<TemplateCreationResponse>(
            mock.perform(post("/template")
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

        assertThat(responseBody.creationNumber).isEqualTo(2)
    }

    @Test
    fun `템플릿 생성하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplateRequest(
                title = "nonExistTitle",
                body = "nonExistBody",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/template")
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
    fun `템플릿 생성하기 - 400 ALREDAY_EXIST_TEMPLATE`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplateRequest(
                title = "savedTitle",
                body = "savedBody",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/template")
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

        assertThat(responseBody.code).isEqualTo("ALREADY_EXIST_TEMPLATE")
    }

    @Test
    fun `템플릿 수정하기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplateRequest(
                title = "nonExistTitle",
                body = "nonExistBody",
            )
        )

        mock.perform(patch("/template/1")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `템플릿 수정하기 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplateRequest(
                title = "nonExistTitle",
                body = "nonExistBody",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/template/1")
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
    fun `템플릿 수정하기 - 404 TEMPLATE_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplateRequest(
                title = "savedTitle",
                body = "savedBody",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/template/2")
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

        assertThat(responseBody.code).isEqualTo("TEMPLATE_NOT_FOUND")
    }

    @Test
    fun `템플릿 삭제하기 - 200`() {
        mock.perform(delete("/template/1")
            .header("Authorization", "this-is-test-token")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `템플릿 삭제하기 - 404 TEMPLATE_NOT_FOUND`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/template/2")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("TEMPLATE_NOT_FOUND")
    }

    @Test
    fun `템플릿 삭제하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/template/1")
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
    fun `템플릿 전체 조회하기 - 200`() {
        val responseBody = objectMapper.readValue<TemplateSearchAllResponse>(
            mock.perform(get("/template")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.templates)
            .map<Long> { it.templateId }
            .containsAll(listOf(1))

        assertThat(responseBody.templates)
            .map<String> { it.templateTitle }
            .containsAll(listOf("savedTitle"))

        assertThat(responseBody.templates)
            .map<String> { it.templateBody }
            .containsAll(listOf("savedBody"))
    }

    @Test
    fun `템플릿 전체 조회하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/template")
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
    fun `템플릿 조회하기 - 200`() {
        val responseBody = objectMapper.readValue<TemplateSearchDetailResponse>(
            mock.perform(get("/template/1")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.templateId).isEqualTo(1)
        assertThat(responseBody.templateTitle).isEqualTo("savedTitle")
        assertThat(responseBody.templateBody).isEqualTo("savedBody")
    }

    @Test
    fun `템플릿 조회하기 - 404 TEMPLATE_NOT_FOUND`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/template/2")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("TEMPLATE_NOT_FOUND")
    }

    @Test
    fun `템플릿 조회하기 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/template/1")
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
