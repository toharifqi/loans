package com.exercise.loans.controller

import com.exercise.loans.dto.Response
import com.exercise.loans.constant.LoanConstant
import com.exercise.loans.dto.Loan
import com.exercise.loans.service.LoanService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "CRUD REST APIs for Loans in X Bank",
    description = "CRUD REST APIs in X Bank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class LoanController(
    private val loanService: LoanService
) {
    
    @PostMapping("/create")
    fun create(
        @RequestParam 
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Response> {
        loanService.createLoan(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                Response(
                    statusCode = LoanConstant.STATUS_201,
                    statusMessage = LoanConstant.MESSAGE_201
                )
            )
    }
    
    @GetMapping("/fetch")
    fun fetch(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Loan> {
        val loan = loanService.fetchLoanInfo(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loan)
    }
    
    @PutMapping("/update")
    fun update(@RequestBody @Valid loan: Loan): ResponseEntity<Response> {
        val isUpdated = loanService.updateLoan(loan)
        
        return if (isUpdated) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_200,
                        statusMessage = LoanConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_417,
                        statusMessage = LoanConstant.MESSAGE_417_UPDATE
                    )
                )
        }
    }
    
    @DeleteMapping("/delete")
    fun delete(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Response> {
        val isDeleted = loanService.deleteLoan(mobileNumber)
        return if (isDeleted) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_200,
                        statusMessage = LoanConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_417,
                        statusMessage = LoanConstant.MESSAGE_417_UPDATE
                    )
                )
        }
    }
    
}
