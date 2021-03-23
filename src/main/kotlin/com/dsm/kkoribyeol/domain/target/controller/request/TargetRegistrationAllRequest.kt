package com.dsm.kkoribyeol.domain.target.controller.request

import com.dsm.kkoribyeol.global.validation.NotDuplicate
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class TargetRegistrationAllRequest(

    @NotDuplicate
    @get:NotEmpty(message = "<NULL> <EMPTY>")
    val targets: List<@Valid TargetRegistrationRequest>
) {

    data class TargetRegistrationRequest(

        @JsonProperty("token")
        @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @get:Size(min = 1, max = 255, message = "<1~255>")
        val targetToken: String,

        @JsonProperty("nickname")
        @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @get:Size(min = 1, max = 255, message = "<1~255>")
        val targetNickname: String,

        @JsonProperty("name")
        @get:Size(max = 12, message = "<~12>")
        val targetName: String?,
    )
}