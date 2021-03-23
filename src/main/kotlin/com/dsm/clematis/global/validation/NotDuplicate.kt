package com.dsm.clematis.global.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotDuplicateValidator::class])
annotation class NotDuplicate(
    val message: String = "request = null",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = [],
)