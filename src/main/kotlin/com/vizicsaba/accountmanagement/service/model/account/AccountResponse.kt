package com.vizicsaba.accountmanagement.service.model.account

import java.math.BigDecimal

data class AccountResponse(
    val accountNumber: BigDecimal,
    val accountHolderName: String,
    val type: String
)
