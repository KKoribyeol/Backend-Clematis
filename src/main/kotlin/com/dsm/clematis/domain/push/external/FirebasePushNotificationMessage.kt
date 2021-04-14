package com.dsm.clematis.domain.push.external

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FirebasePushNotificationMessage(
    val validateOnly: Boolean,
    val message: FirebaseMessage,
) {

    data class FirebaseMessage(
        val token: String,
        val notification: FirebaseNotification,
    )

    data class FirebaseNotification(
        val title: String,
        val body: String,
    )
}