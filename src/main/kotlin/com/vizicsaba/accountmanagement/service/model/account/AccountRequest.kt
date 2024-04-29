package com.vizicsaba.accountmanagement.service.model.account

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for the creation of an account.")
data class AccountRequest(
    @field:Schema(
        description = "The name of the account holder",
        example = "Test User",
        type = "String"
    ) val accountHolderName: String)
