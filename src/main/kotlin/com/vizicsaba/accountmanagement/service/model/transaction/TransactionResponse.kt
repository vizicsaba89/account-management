package com.vizicsaba.accountmanagement.service.model.transaction

import java.math.BigDecimal
import java.sql.Timestamp

data class TransactionResponse(
        val id: Long?,
        val accountNumber: BigDecimal,
        val type: String,
        val amount: Long,
        val timestamp: Timestamp
)
