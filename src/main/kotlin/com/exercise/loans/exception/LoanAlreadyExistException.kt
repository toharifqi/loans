package com.exercise.loans.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class LoanAlreadyExistException(
    message: String,
): RuntimeException() {
    val responseMessage: String = message
}
