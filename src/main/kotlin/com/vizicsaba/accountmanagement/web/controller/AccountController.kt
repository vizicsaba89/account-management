package com.vizicsaba.accountmanagement.web.controller

import com.vizicsaba.accountmanagement.service.AccountService
import com.vizicsaba.accountmanagement.service.model.AccountRequest
import com.vizicsaba.accountmanagement.service.model.AccountResponse
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.net.URI

@RestController
class AccountController(private val accountService: AccountService) {

    @GetMapping("/accounts")
    suspend fun getAllAccounts(): ResponseEntity<Flow<AccountResponse>> {
        val accounts = accountService.findAllActiveAccounts()

        return ResponseEntity.ok(accounts)
    }

    @GetMapping("/accounts/{accountNumber}")
    suspend fun getAccountByAccountNumber(@PathVariable accountNumber: BigDecimal): ResponseEntity<AccountResponse> {
        val account = accountService.findByAccountNumber(accountNumber)

        return if (account != null) ResponseEntity.ok(account)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/accounts")
    suspend fun createAccount(@RequestBody accountRequest: AccountRequest): ResponseEntity<AccountResponse> {
        val account = accountService.createAccount(accountRequest)

        return ResponseEntity.created(URI.create("/accounts/${account?.accountNumber}")).body(account)
    }

    @DeleteMapping("/accounts/{accountNumber}")
    suspend fun deleteAccount(@PathVariable accountNumber: BigDecimal): ResponseEntity<Unit> {
        val account = accountService.deleteAccount(accountNumber)

        return ResponseEntity.ok().build()
    }
}
