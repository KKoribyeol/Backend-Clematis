package com.dsm.kkoribyeol.global.exception.handler

import com.dsm.kkoribyeol.global.exception.CommonException
import com.dsm.kkoribyeol.global.exception.response.CommonExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun authenticationExceptionHandler(e: AuthenticationException) =
        CommonExceptionResponse(
            code = "INVALID_TOKEN",
            message = "토큰이 잘못되었습니다.",
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidateExceptionHandler(e: MethodArgumentNotValidException) =
        CommonExceptionResponse(
            code = "INVALID_REQUEST_BODY",
            message = "클라이언트의 요청이 잘못되었습니다. [${e.bindingResult.allErrors.first().defaultMessage}]",
        )

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidationExceptionHandler(e: ConstraintViolationException) =
        CommonExceptionResponse(
            code = "INVALID_REQUEST_BODY",
            message = e.localizedMessage,
        )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidationExceptionHandler(e: HttpMessageNotReadableException) =
        CommonExceptionResponse(
            code = "INVALID_REQUEST_BODY",
            message = "이상한 필드가 존재합니다. [${e.mostSpecificCause.localizedMessage}]"
        )

    @ExceptionHandler(CommonException::class)
    fun commonExceptionHandler(e: CommonException) =
        ResponseEntity(
            CommonExceptionResponse(
                code = e.code,
                message = e.message?: "알 수 없는 오류",
            ),
            e.status,
        )

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): ResponseEntity<CommonExceptionResponse> {
        e.printStackTrace()
        return ResponseEntity(
            CommonExceptionResponse(
                code = "INTERNAL_SERVER_ERROR",
                message = e.message?: "알 수 없는 오류",
            ),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}