package com.dsm.clematis.global.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotDuplicateValidator::class])
annotation class NotDuplicate(
    val message: String = "중복된 값이 있을 수 없습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = [],
)