package com.dsm.clematis.domain.account.controller

import com.dsm.clematis.domain.account.controller.request.JoinRequest
import com.dsm.clematis.domain.account.controller.request.LoginRequest
import com.dsm.clematis.domain.account.controller.request.AccountNameModificationRequest
import com.dsm.clematis.domain.account.controller.request.AccountPasswordModificationRequest
import com.dsm.clematis.domain.account.controller.response.AccountNameResponse
import com.dsm.clematis.domain.account.controller.response.LoginResponse
import com.dsm.clematis.global.exception.entrypoint.InvalidTokenExceptionEntryPoint
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

        mock.perform(post("/account")
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
            mock.perform(post("/account")
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
            mock.perform(post("/account/login")
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
            mock.perform(post("/account/login")
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
            mock.perform(post("/account/login")
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
    fun `비밀번호 변경 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            AccountPasswordModificationRequest(
                newPassword = "newPassword",
                confirmNewPassword = "newPassword",
            )
        )

        mock.perform(patch("/account/password")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `비밀번호 변경 - 400 PASSWORD_MISMATCH`() {
        val requestBody = objectMapper.writeValueAsString(
            AccountPasswordModificationRequest(
                newPassword = "newPassword",
                confirmNewPassword = "invalidPassword",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/account/password")
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

        assertThat(responseBody.code).isEqualTo("PASSWORD_MISMATCH")
    }

    @Test
    fun `비밀번호 변경 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            AccountPasswordModificationRequest(
                newPassword = "newPassword",
                confirmNewPassword = "newPassword",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/account/password")
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
    fun `이름 변경 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            AccountNameModificationRequest(
                newName = "newName",
            )
        )

        mock.perform(patch("/account/name")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `이름 변경 - 401 INVALID_TOKEN`() {
        val requestBody = objectMapper.writeValueAsString(
            AccountNameModificationRequest(
                newName = "newName",
            )
        )

        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(patch("/account/name")
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
    fun `계정 탈퇴 - 200`() {
        mock.perform(delete("/account")
            .header("Authorization", "this-is-test-token")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `계정 탈퇴 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(delete("/account")
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
    fun `이름 반환 - 200`() {
        val responseBody = objectMapper.readValue<AccountNameResponse>(
            mock.perform(get("/account/name")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(responseBody.name).isEqualTo("savedName")
    }

    @Test
    fun `이름 반환 - 401 INVALID_TOKEN`() {
        val responseBody = objectMapper.readValue<CommonExceptionResponse>(
            mock.perform(get("/account/name")
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
