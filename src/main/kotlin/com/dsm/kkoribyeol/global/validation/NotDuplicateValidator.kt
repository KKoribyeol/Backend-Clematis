package com.dsm.kkoribyeol.global.validation

import com.dsm.kkoribyeol.domain.target.controller.request.TargetRegistrationAllRequest.TargetRegistrationRequest
import com.dsm.kkoribyeol.global.exception.InvalidRequestException
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotDuplicateValidator : ConstraintValidator<NotDuplicate, List<TargetRegistrationRequest>> {

    override fun isValid(
        value: List<TargetRegistrationRequest>?,
        context: ConstraintValidatorContext?
    ) = !isContainDuplicateElement(value ?: throw InvalidRequestException("request = null"))

    private fun isContainDuplicateElement(value: List<TargetRegistrationRequest>) =
        value.size != value.distinct().count()
}