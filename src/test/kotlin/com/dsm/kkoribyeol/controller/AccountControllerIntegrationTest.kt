package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.JoinRequest
import com.dsm.kkoribyeol.controller.request.LoginRequest
import com.dsm.kkoribyeol.controller.response.LoginResponse
import com.dsm.kkoribyeol.exception.handler.CommonExceptionResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class AccountControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `회원가입 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            JoinRequest(
                accountId = "idIdIdId",
                accountPassword = "password",
                accountName = "nameName",
            )
        )

        mock.perform(post("/auth/join")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `회원가입 - 400 ALREADY_EXIST_ACCOUNT`() {
        val requestBody = objectMapper.writeValueAsString(
            JoinRequest(
                accountId = "savedIdId",
                accountPassword = "savedPassword",
                accountName = "savedName",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/auth/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("ALREADY_EXIST_ACCOUNT")
    }

    @Test
    fun `로그인 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                accountId = "savedIdId",
                accountPassword = "savedPassword",
            )
        )

        val responseBody = objectMapper.readValue<LoginResponse>(
            mock.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.accessToken).isEqualTo("this-is-test-token")
        assertThat(responseBody.refreshToken).isEqualTo("this-is-test-token")
    }

    @Test
    fun `로그인 - 404 ACCOUNT_NOT_FOUND`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                accountId = "nonExistId",
                accountPassword = "nonExistPassword",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("ACCOUNT_NOT_FOUND")
    }

    @Test
    fun `로그인 - 400 PASSWORD_MISMATCH`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                accountId = "savedIdId",
                accountPassword = "nonExistPassword",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.code).isEqualTo("PASSWORD_MISMATCH")
    }
}