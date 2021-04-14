package com.dsm.clematis.domain.push.controller.request

import com.dsm.clematis.global.validation.NotDuplicate
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class PushRequest(

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    @NotDuplicate
    val targetTokens: List<String>,

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    @NotDuplicate
    val targetGroups: List<String>,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val title: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val body: String,

    @get:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @get:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val reservationDate: LocalDateTime?
)