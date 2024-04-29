package com.vizicsaba.accountmanagement.service.model.transaction

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for the actual balance response.")
data class BalanceResponse(val balance: Long)
