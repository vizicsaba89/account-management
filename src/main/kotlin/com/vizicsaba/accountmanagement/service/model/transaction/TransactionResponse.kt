package com.vizicsaba.accountmanagement.service.model.transaction

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.sql.Timestamp

@Schema(description = "Model for the transaction response.")
data class TransactionResponse(
        @field:Schema(
                description = "The transaction id",
                example = "1",
                type = "Long"
        ) val id: Long?,
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
        ) val amount: Long,
        @field:Schema(
                description = "The transaction time",
                example = "2019-10-11T09:21:04.981Z",
                type = "TimeStamp"
        ) val timestamp: Timestamp
)
