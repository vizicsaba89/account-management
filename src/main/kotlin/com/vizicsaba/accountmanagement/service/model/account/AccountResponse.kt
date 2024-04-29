package com.vizicsaba.accountmanagement.service.model.account

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Model for the account response.")
data class AccountResponse(
    @field:Schema(
        description = "The account number, generated 24 digits",
        example = "123456788876399894596841",
        type = "BigDecimal"
    ) val accountNumber: BigDecimal,
    @field:Schema(
        description = "The name of the account holder",
        example = "Test User",
        type = "String"
    ) val accountHolderName: String
)
