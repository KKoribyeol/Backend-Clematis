package com.dsm.kkoribyeol.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidateExceptionHandler(e: MethodArgumentNotValidException) =
        CommonExceptionResponse(
            code = "INVALID_REQUEST",
            message = "클라이언트의 요청이 잘못되었습니다. [${e.bindingResult.allErrors.first().defaultMessage}]",
        )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidateExceptionHandler(e: MethodArgumentTypeMismatchException) =
        CommonExceptionResponse(
            code = "INVALID_REQUEST",
            message = "${e.mostSpecificCause.message}",
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