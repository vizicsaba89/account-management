package com.vizicsaba.accountmanagement.web.controller

import com.vizicsaba.accountmanagement.service.AccountService
import com.vizicsaba.accountmanagement.service.model.account.AccountRequest
import com.vizicsaba.accountmanagement.service.model.account.AccountResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.net.URI

@RestController
class AccountController(private val accountService: AccountService) {

    @Operation(summary = "Returns all accounts", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
        ]
    )
    @GetMapping("/accounts")
    suspend fun getAllAccounts(): ResponseEntity<Flow<AccountResponse>> {
        val accounts = accountService.findAllActiveAccounts()

        return ResponseEntity.ok(accounts)
    }

    @Operation(summary = "Returns account by account id", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "If account not found or deleted"),
        ]
    )
    @GetMapping("/accounts/{accountNumber}")
    suspend fun getAccountByAccountNumber(@PathVariable accountNumber: BigDecimal): ResponseEntity<AccountResponse> {
        val account = accountService.findByAccountNumber(accountNumber)

        return if (account != null) ResponseEntity.ok(account)
        else ResponseEntity.notFound().build()
    }

    @Operation(summary = "Creates account", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Successful operation"),
        ]
    )
    @PostMapping("/accounts")
    suspend fun createAccount(@RequestBody accountRequest: AccountRequest): ResponseEntity<AccountResponse> {
        val account = accountService.createAccount(accountRequest)

        return ResponseEntity.created(URI.create("/accounts/${account?.accountNumber}")).body(account)
    }

    @Operation(summary = "Marks account as deleted", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "If account not found or deleted"),
        ]
    )
    @DeleteMapping("/accounts/{accountNumber}")
    suspend fun deleteAccount(@PathVariable accountNumber: BigDecimal): ResponseEntity<Unit> {
        val account = accountService.deleteAccount(accountNumber)

        return ResponseEntity.ok().build()
    }
}
