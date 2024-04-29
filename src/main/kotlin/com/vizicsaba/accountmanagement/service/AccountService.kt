package com.vizicsaba.accountmanagement.service

import com.vizicsaba.accountmanagement.configuration.AppConfiguration
import com.vizicsaba.accountmanagement.data.model.account.Account
import com.vizicsaba.accountmanagement.data.model.account.AccountState
import com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck.BackGroundSecurityCheckRequest
import com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck.BackGroundSecurityCheckResponse
import com.vizicsaba.accountmanagement.data.repository.AccountRepository
import com.vizicsaba.accountmanagement.service.exception.InvalidAccountException
import com.vizicsaba.accountmanagement.service.model.account.AccountRequest
import com.vizicsaba.accountmanagement.service.model.account.AccountResponse
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.ThreadLocalRandom


@Service
class AccountService(private val accountRepository: AccountRepository, private val backGroundSecurityCheckService: BackGroundSecurityCheckService, private val appConfiguration: AppConfiguration) {
    suspend fun findByAccountNumber(accountNumber: BigDecimal): AccountResponse? =
            accountRepository.findByAccountNumber(accountNumber)?.let { AccountResponse(it.accountNumber, it.accountHolderName) }

    suspend fun performBackGroundSecurityCheck(accountRequest: AccountRequest) {
        val account = accountRepository.save(Account(getAccountNumber(), accountRequest.accountHolderName, AccountState.PRECHECK))
                .let { AccountResponse(it.accountNumber, it.accountHolderName) }

        backGroundSecurityCheckService.getBackGroundSecurityCheckResult(BackGroundSecurityCheckRequest(account.accountNumber, account.accountHolderName, appConfiguration.callbackUrl))
    }

    suspend fun createAccount(backGroundSecurityCheckResponse: BackGroundSecurityCheckResponse): AccountResponse? {
        if (!backGroundSecurityCheckResponse.isSecurityCheckSuccess) {
            throw InvalidAccountException("Invalid Account State!")
        }

        val accountNumber = BigDecimal(backGroundSecurityCheckResponse.accountNumber)
        accountRepository.updateAccountState(accountNumber)

        return accountRepository.findByAccountNumber(accountNumber)
                .let { it?.let { it1 -> AccountResponse(it1.accountNumber, it.accountHolderName) } }
    }

    suspend fun deleteAccount(accountNumber: BigDecimal): Unit? =
            accountRepository.deleteAccount(accountNumber)

    suspend fun findAllActiveAccounts() = accountRepository.findAll()
            .filter { it.accountState != AccountState.DELETED }
            .map { AccountResponse(it.accountNumber, it.accountHolderName) }

    private fun getAccountNumber(): BigDecimal {
        val bankIdentifier: Long = appConfiguration.bankIdentifier
        val random: Long = ThreadLocalRandom.current().nextLong(1000_0000_0000_0000L, 9999_9999_9999_9999L)

        return String.format("%s%s", bankIdentifier, random).toBigDecimal()
    }
}
