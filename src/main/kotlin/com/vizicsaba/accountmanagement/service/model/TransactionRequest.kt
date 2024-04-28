package com.vizicsaba.accountmanagement.service.model

import java.math.BigDecimal

data class TransactionRequest(
        val accountNumber: BigDecimal,
        val type: String,
        val amount: Long
)
