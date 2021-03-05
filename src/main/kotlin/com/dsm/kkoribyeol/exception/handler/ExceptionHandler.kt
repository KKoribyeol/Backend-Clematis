package com.dsm.kkoribyeol.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        ExceptionResponse(
            code = "INVALID_REQUEST_BODY",
            message = "클라이언트의 요청이 잘못되었습니다. [${e.bindingResult.allErrors.first().defaultMessage}]",
        )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidateExceptionHandler(e: MethodArgumentTypeMismatchException) =
        ExceptionResponse(
            code = "INVALID_REQUEST_BODY",
            message = "${e.mostSpecificCause.message}",
        )

    @ExceptionHandler(CommonException::class)
    fun commonExceptionHandler(e: CommonException) =
        ResponseEntity(
            ExceptionResponse(
                code = e.code,
                message = e.message?: "큰 문제는 아닌데 이거 나오면 안 되긴 함",
            ),
            e.status,
        )

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): ResponseEntity<ExceptionResponse> {
        e.printStackTrace()
        return ResponseEntity(
            ExceptionResponse(
                code = "INTERNAL_SERVER_ERROR",
                message = "큰 문제긴 한데 이거 나오면 안 되긴 함",
            ),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}