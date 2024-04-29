package com.vizicsaba.accountmanagement.service

import com.vizicsaba.accountmanagement.data.model.account.AccountState
import com.vizicsaba.accountmanagement.data.model.transaction.Transaction
import com.vizicsaba.accountmanagement.data.model.transaction.TransactionType
import com.vizicsaba.accountmanagement.data.repository.AccountRepository
import com.vizicsaba.accountmanagement.data.repository.TransactionRepository
import com.vizicsaba.accountmanagement.service.exception.InvalidAccountException
import com.vizicsaba.accountmanagement.service.model.transaction.BalanceResponse
import com.vizicsaba.accountmanagement.service.model.transaction.TransactionRequest
import com.vizicsaba.accountmanagement.service.model.transaction.TransactionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*


@Service
class TransactionService(private val accountRepository: AccountRepository, private val transactionRepository: TransactionRepository) {

    suspend fun getCurrentBalance(accountNumber: BigDecimal): BalanceResponse? {
        val account = accountRepository.findByAccountNumber(accountNumber)

        return if (account?.accountState == AccountState.ACTIVE) {
            val balance = transactionRepository.findByAccountNumberAndTimestampBefore(accountNumber, Timestamp(Date().time))
                    .map { if (it.type == TransactionType.DEPOSIT) it.amount else it.amount.unaryMinus() }
                    .toList()
                    .sum()

            BalanceResponse(balance)
        } else throw InvalidAccountException("Invalid Account State!")
    }

    suspend fun getTransactions(accountNumber: BigDecimal): Flow<TransactionResponse> {
        val account = accountRepository.findByAccountNumber(accountNumber)

        return if (account?.accountState == AccountState.ACTIVE) {
            transactionRepository.findByAccountNumber(accountNumber)
                    .map { TransactionResponse(it.id, it.accountNumber, it.type.name, it.amount, it.timestamp) }
        } else throw RuntimeException("Invalid Account State!")
    }

    suspend fun createTransaction(transactionRequest: TransactionRequest): TransactionResponse? {
        return transactionRepository.save(
                Transaction(
                        null,
                        transactionRequest.accountNumber,
                        TransactionType.valueOf(transactionRequest.type),
                        transactionRequest.amount,
                        Timestamp(Date().time)
                )
        ).let { TransactionResponse(it.id, it.accountNumber, it.type.name, it.amount, it.timestamp) }
    }

}
