package com.exercise.loans.service

import com.exercise.accounts.exception.ResourceNotFoundException
import com.exercise.loans.constant.LoanConstant
import com.exercise.loans.dto.Loan
import com.exercise.loans.entity.LoanEntity
import com.exercise.loans.exception.LoanAlreadyExistException
import com.exercise.loans.repository.LoanRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

interface LoanService {
    fun create(mobileNumber: String)
    
    fun fetch(mobileNumber: String): Loan
    
    fun update(loan: Loan): Boolean
    
    fun delete(mobileNumber: String): Boolean
}

@Service
class LoanServiceImpl(
    private val loanRepository: LoanRepository
) : LoanService {
    override fun create(mobileNumber: String) {
        checkIfLoanAlreadyExist(mobileNumber)
        loanRepository.save(generateLoan(mobileNumber))
    }
    
    private fun checkIfLoanAlreadyExist(mobileNumber: String) {
        val optionalLoan = loanRepository.findByMobileNumber(mobileNumber)
        if (optionalLoan.isPresent) {
            throw LoanAlreadyExistException("Loan already registered with given mobileNumber $mobileNumber")
        }
    }
    
    private fun generateLoan(mobileNumber: String): LoanEntity {
        val randomLoanNumber = 1000000000L + Random.nextInt(900000000)
        return LoanEntity(
            mobileNumber = mobileNumber,
            loanNumber = randomLoanNumber.toString(),
            loanType = LoanConstant.HOME_LOAN,
            totalLoan = LoanConstant.NEW_LOAN_LIMIT,
            amountPaid = 0,
            outstandingAmount = LoanConstant.NEW_LOAN_LIMIT,
        )
    }

    override fun fetch(mobileNumber: String): Loan {
        val loanEntity = loanRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        }
        
        return Loan(loanEntity)
    }

    override fun update(loan: Loan): Boolean {
        val loanEntity = loanRepository.findByMobileNumber(loan.mobileNumber).orElseThrow {
            ResourceNotFoundException("Loan", "mobileNumber", loan.mobileNumber)
        }
        
        val updatedLoan = LoanEntity(loan)
        loanRepository.save(updatedLoan)
        return true
    }

    override fun delete(mobileNumber: String): Boolean {
        val loanEntity = loanRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        }
        
        loanRepository.deleteById(loanEntity.loanId)
        return true
    }
}
