package com.vizicsaba.accountmanagement.data.repository

import com.vizicsaba.accountmanagement.data.model.Account
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal

interface AccountRepository : CoroutineCrudRepository<Account, BigDecimal> {
    suspend fun findByAccountNumber(accountNumber: BigDecimal): Account?
}
