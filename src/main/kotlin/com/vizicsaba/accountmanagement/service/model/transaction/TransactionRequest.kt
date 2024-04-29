package com.vizicsaba.accountmanagement.service.model.transaction

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Model for the transaction request.")
data class TransactionRequest(
        @field:Schema(
                description = "The account number, generated 24 digits",
                example = "123456788876399894596841",
                type = "BigDecimal"
        ) val accountNumber: BigDecimal,
        @field:Schema(
                description = "The transaction type, deposit or withdrawal",
                example = "DEPOSIT",
                type = "String"
        ) val type: String,
        @field:Schema(
                description = "The transaction amount",
                example = "500",
                type = "Long"
        ) val amount: Long
)
