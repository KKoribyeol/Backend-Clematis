package com.dsm.clematis.domain.push.controller.request

import com.dsm.clematis.global.validation.NotDuplicate
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TemplatePushRequest(

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    @NotDuplicate
    val targetTokens: List<String>,

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    @NotDuplicate
    val targetGroups: List<String>,

    @get:NotNull(message = "<NULL>")
    val templateId: Long,

    @get:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @get:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val reservationDate: LocalDateTime?
)