package com.vizicsaba.accountmanagement.data.model.account

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("accounts")
data class Account(
    @Id val accountNumber: BigDecimal,
    @Column("account_holder_name") val accountHolderName: String,
    @Column("account_state") val accountState: AccountState,
): Persistable<BigDecimal> {
    override fun getId(): BigDecimal = accountNumber

    override fun isNew(): Boolean = true
}

enum class AccountState {
    ACTIVE,
    DELETED
}
