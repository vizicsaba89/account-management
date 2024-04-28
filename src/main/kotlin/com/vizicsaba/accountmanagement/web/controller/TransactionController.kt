package com.vizicsaba.accountmanagement.web.controller

import com.vizicsaba.accountmanagement.service.TransactionService
import com.vizicsaba.accountmanagement.service.model.BalanceResponse
import com.vizicsaba.accountmanagement.service.model.TransactionRequest
import com.vizicsaba.accountmanagement.service.model.TransactionResponse
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping("/accounts/{accountNumber}/balance")
    suspend fun getCurrentBalanceByAccountNumber(@PathVariable accountNumber: BigDecimal): ResponseEntity<BalanceResponse> {
        val balanceResponse = transactionService.getCurrentBalance(accountNumber)

        return ResponseEntity.ok(balanceResponse)
    }

    @GetMapping("/accounts/{accountNumber}/transactions")
    suspend fun getTransactions(@PathVariable accountNumber: BigDecimal): ResponseEntity<Flow<TransactionResponse>> {
        val transactionResponse = transactionService.getTransactions(accountNumber)

        return ResponseEntity.ok(transactionResponse)
    }

    @PostMapping("/accounts/{accountNumber}/transactions")
    suspend fun createTransaction(@PathVariable accountNumber: BigDecimal, @RequestBody transactionRequest: TransactionRequest): ResponseEntity<TransactionResponse> {
        val transactionResponse = transactionService.createTransaction(transactionRequest)

        return ResponseEntity.ok(transactionResponse)
    }
}