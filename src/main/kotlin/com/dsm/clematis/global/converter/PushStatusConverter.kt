package com.dsm.clematis.global.converter

import com.dsm.clematis.global.attribute.PushStatus
import com.dsm.clematis.global.exception.NonExistPushStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class PushStatusConverter : AttributeConverter<PushStatus, String> {

    override fun convertToDatabaseColumn(status: PushStatus) =
        status.status

    override fun convertToEntityAttribute(status: String) =
        PushStatus.values().singleOrNull { it.status == status }
            ?: throw NonExistPushStatusException(status)
}