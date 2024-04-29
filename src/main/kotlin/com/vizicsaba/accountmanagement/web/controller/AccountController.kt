package com.vizicsaba.accountmanagement.web.controller

import com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck.BackGroundSecurityCheckResponse
import com.vizicsaba.accountmanagement.service.AccountService
import com.vizicsaba.accountmanagement.service.model.account.AccountRequest
import com.vizicsaba.accountmanagement.service.model.account.AccountResponse
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.math.BigInteger
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
    suspend fun performBackGroundSecurityCheck(@RequestBody accountRequest: AccountRequest) {
        accountService.performBackGroundSecurityCheck(accountRequest)
    }

    @PostMapping("/accounts/continue")
    suspend fun createAccount(@RequestBody backGroundSecurityCheckResponse: BackGroundSecurityCheckResponse): ResponseEntity<AccountResponse> {
        val account = accountService.createAccount(backGroundSecurityCheckResponse)

        return ResponseEntity.created(URI.create("/accounts/${account?.accountNumber}")).body(account)
    }

    @DeleteMapping("/accounts/{accountNumber}")
    suspend fun deleteAccount(@PathVariable accountNumber: BigDecimal): ResponseEntity<Unit> {
        val account = accountService.deleteAccount(accountNumber)

        return ResponseEntity.ok().build()
    }
}
