package com.dsm.clematis.domain.push.external

import com.dsm.clematis.domain.push.external.FirebasePushNotificationMessage.FirebaseMessage
import retrofit2.Call
import retrofit2.http.*

interface FirebaseConnection {

    @Headers(value = ["accept: application/json", "content-type: application/json"])
    @POST
    fun sendPushNotificationToFirebase(
        @Header("Authorization") authorization: String,
        @Url uri: String,
        @Body pushNotificationMessage: FirebasePushNotificationMessage,
    ): Call<FirebaseMessage>
}