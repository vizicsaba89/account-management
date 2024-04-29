package com.vizicsaba.accountmanagement.service

import com.vizicsaba.accountmanagement.configuration.AppConfiguration
import com.vizicsaba.accountmanagement.data.model.account.Account
import com.vizicsaba.accountmanagement.data.model.account.AccountState
import com.vizicsaba.accountmanagement.data.repository.AccountRepository
import com.vizicsaba.accountmanagement.service.model.account.AccountRequest
import com.vizicsaba.accountmanagement.service.model.account.AccountResponse
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.ThreadLocalRandom


@Service
class AccountService(private val accountRepository: AccountRepository, private val client: DatabaseClient, private val appConfiguration: AppConfiguration) {
    suspend fun findByAccountNumber(accountNumber: BigDecimal): AccountResponse? =
            accountRepository.findByAccountNumber(accountNumber)?.let { AccountResponse(it.accountNumber, it.accountHolderName) }

    suspend fun createAccount(accountRequest: AccountRequest): AccountResponse? {
        return accountRepository.save(Account(getAccountNumber(), accountRequest.accountHolderName, AccountState.ACTIVE))
                .let { AccountResponse(it.accountNumber, it.accountHolderName) }
    }

    suspend fun deleteAccount(accountNumber: BigDecimal): Unit? =
            client.sql("UPDATE accounts SET account_state = 'DELETED' WHERE account_number = $accountNumber")
                    .await()

    suspend fun findAllActiveAccounts() = accountRepository.findAll()
            .filter { it.accountState == AccountState.ACTIVE }
            .map { AccountResponse(it.accountNumber, it.accountHolderName) }

    private fun getAccountNumber(): BigDecimal {
        val bankIdentifier: Long = appConfiguration.bankIdentifier
        val random: Long = ThreadLocalRandom.current().nextLong(1000_0000_0000_0000L, 9999_9999_9999_9999L)

        return String.format("%s%s", bankIdentifier, random).toBigDecimal()
    }
}
