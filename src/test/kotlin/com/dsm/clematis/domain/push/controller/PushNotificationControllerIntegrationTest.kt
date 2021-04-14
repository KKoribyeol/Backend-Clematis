package com.dsm.clematis.domain.push.controller

import com.dsm.clematis.domain.push.controller.request.PushRequest
import com.dsm.clematis.domain.push.controller.request.TemplatePushRequest
import com.dsm.clematis.domain.push.controller.response.PushNotificationHistoryResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.aspectj.lang.annotation.Before
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.View
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView
import java.time.LocalDateTime
import java.util.*

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class PushNotificationControllerIntegrationTest(
    private val pushNotificationController: PushNotificationController,
    private val objectMapper: ObjectMapper,
) {
    private lateinit var mock: MockMvc

    @BeforeEach
    fun setup() {
        mock = MockMvcBuilders
            .standaloneSetup(pushNotificationController)
            .setCustomArgumentResolvers(
                PageableHandlerMethodArgumentResolver()
            ).setViewResolvers(object : ViewResolver {
                override fun resolveViewName(viewName: String, locale: Locale) =
                    MappingJackson2JsonView()
            }).build()
    }

    @Test
    fun `푸시 알림 보내기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            PushRequest(
                targetTokens = listOf("savedToken"),
                targetGroups = listOf("savedName"),
                title = "savedTitle",
                body = "savedBody",
                reservationDate = null,
            )
        )

        mock.perform(post("/push")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `예약 푸시 알림 보내기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            PushRequest(
                targetTokens = listOf("savedToken"),
                targetGroups = listOf("savedName"),
                title = "savedTitle",
                body = "savedBody",
                reservationDate = LocalDateTime.now(),
            )
        )

        mock.perform(post("/push")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `템플릿 푸시 알림 보내기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplatePushRequest(
                targetTokens = listOf("savedToken"),
                targetGroups = listOf("savedName"),
                templateId = 1,
                reservationDate = null,
            )
        )

        mock.perform(post("/template-push")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `예약 템플릿 푸시 알림 보내기 - 200`() {
        val requestBody = objectMapper.writeValueAsString(
            TemplatePushRequest(
                targetTokens = listOf("savedToken"),
                targetGroups = listOf("savedName"),
                templateId = 1,
                reservationDate = LocalDateTime.now(),
            )
        )

        mock.perform(post("/template-push")
            .header("projectCode", "savedProject-finally")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }
}