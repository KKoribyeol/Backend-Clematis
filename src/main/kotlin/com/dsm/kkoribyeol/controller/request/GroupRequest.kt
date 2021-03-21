package com.dsm.kkoribyeol.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class GroupRequest(

    @JsonProperty("name")
    @get:Size(min = 1, max = 20, message = "<1~20>")
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val groupName: String?,
)