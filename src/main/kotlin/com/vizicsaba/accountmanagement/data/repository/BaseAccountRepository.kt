package com.vizicsaba.accountmanagement.data.repository

import com.vizicsaba.accountmanagement.data.model.account.Account
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal

interface BaseAccountRepository : CoroutineCrudRepository<Account, BigDecimal> {
    suspend fun findByAccountNumber(accountNumber: BigDecimal): Account?
}
