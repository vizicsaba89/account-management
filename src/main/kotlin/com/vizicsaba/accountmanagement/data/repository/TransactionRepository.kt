package com.vizicsaba.accountmanagement.data.repository

import com.vizicsaba.accountmanagement.data.model.Account
import com.vizicsaba.accountmanagement.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal

interface TransactionRepository : CoroutineCrudRepository<Transaction, Long> {
    suspend fun findByAccountNumber(accountNumber: BigDecimal): Flow<Transaction>
}
