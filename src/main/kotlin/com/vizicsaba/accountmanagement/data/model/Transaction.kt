package com.vizicsaba.accountmanagement.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.sql.Timestamp

@Table("transactions")
data class Transaction(
    @Id val id: Long?,
    @Column("account_number") val accountNumber: BigDecimal,
    @Column("type") val type: TransactionType,
    @Column("amount") val amount: Long,
    @Column("time_stamp") val timestamp: Timestamp,
)