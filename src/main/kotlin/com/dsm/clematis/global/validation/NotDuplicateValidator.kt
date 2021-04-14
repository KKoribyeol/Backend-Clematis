package com.dsm.clematis.global.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotDuplicateValidator : ConstraintValidator<NotDuplicate, Collection<Any?>> {

    override fun isValid(
        value: Collection<Any?>,
        context: ConstraintValidatorContext,
    ) = !isContainDuplicateElement(value)
            && !isContainNullElement(value)

    private fun isContainDuplicateElement(value: Collection<Any?>) =
        value.size != value.distinct().count()

    private fun isContainNullElement(value: Collection<Any?>) =
        value.any { it == null }
}