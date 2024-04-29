package com.vizicsaba.accountmanagement.data.repository

import com.vizicsaba.accountmanagement.data.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal
import java.sql.Timestamp

interface TransactionRepository : CoroutineCrudRepository<Transaction, Long> {
    suspend fun findByAccountNumber(accountNumber: BigDecimal): Flow<Transaction>
    suspend fun findByAccountNumberAndTimestampBefore(accountNumber: BigDecimal, timestamp: Timestamp): Flow<Transaction>
}
