package com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck

import java.math.BigDecimal

data class BackGroundSecurityCheckRequest(
        val accountNumber: BigDecimal,
        val accountHolderName: String,
        val callbackUrl: String
)
