package com.dsm.clematis.global.attribute

enum class PushStatus(val status: String, val isComplete: Boolean) {
    SUCCESS("success", true),
    FAILURE("failure", false),
    WAITING("waiting", false),
}