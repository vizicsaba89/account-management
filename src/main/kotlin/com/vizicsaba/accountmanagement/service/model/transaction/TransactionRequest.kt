package com.vizicsaba.accountmanagement.service.model.transaction

import java.math.BigDecimal

data class TransactionRequest(
        val accountNumber: BigDecimal,
        val type: String,
        val amount: Long
)
