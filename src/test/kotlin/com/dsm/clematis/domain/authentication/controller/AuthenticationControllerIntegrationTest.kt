package com.dsm.clematis.domain.authentication.controller

import com.dsm.clematis.domain.authentication.controller.request.LoginRequest
import com.dsm.clematis.domain.authentication.controller.request.LogoutRequest
import com.dsm.clematis.domain.authentication.controller.request.ReissueTokenRequest
import com.dsm.clematis.domain.authentication.controller.response.LoginResponse
import com.dsm.clematis.domain.authentication.controller.response.TokenResponse
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class AuthenticationControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `로그인 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                accountId = "savedIdId",
                accountPassword = "savedPassword",
            )
        )

        val responseBody = objectMapper.readValue<LoginResponse>(
            mock.perform(
                post("/auth")
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
            mock.perform(
                post("/auth")
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
            mock.perform(
                post("/auth")
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

    @Test
    fun `로그아웃 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            LogoutRequest(
                token = "this-is-test-token",
            )
        )

        mock.perform(
            delete("/auth")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `로그아웃 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            LogoutRequest(
                token = "this-is-invalid-token",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(
                delete("/auth")
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
    fun `토큰 재발급 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            ReissueTokenRequest(
                accessToken = "this-is-test-token",
                refreshToken = "this-is-test-token",
            )
        )

        val responseBody = objectMapper.readValue<TokenResponse>(
            mock.perform(post("/auth/token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )


        assertThat(responseBody.token).isEqualTo("this-is-test-token")
    }

    @Test
    fun `토큰 재발급 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            ReissueTokenRequest(
                accessToken = "this-is-invalid-token",
                refreshToken = "this-is-invalid-token",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(post("/auth/token")
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
}