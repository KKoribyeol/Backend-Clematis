package com.dsm.kkoribyeol.controller.request

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class TargetRegistrationAllRequest(

    @get:NotBlank(message = "<NULL> <BLANK>")
    val targets: List<@Valid TargetRegistrationRequest>
) {

    data class TargetRegistrationRequest(

        @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @get:Size(min = 1, max = 255, message = "<1~255>")
        val targetToken: String,

        @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @get:Size(min = 1, max = 255, message = "<1~255>")
        val targetNickname: String,

        @get:Size(max = 12, message = "<~12>")
        val targetName: String?,
    )
}