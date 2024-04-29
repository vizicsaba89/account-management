package com.vizicsaba.accountmanagement.data.repository

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class AccountRepository(private val baseAccountRepository: BaseAccountRepository, private val client: DatabaseClient): BaseAccountRepository by baseAccountRepository {

    suspend fun deleteAccount(accountNumber: BigDecimal): Unit? =
            client.sql("UPDATE accounts SET account_state = 'DELETED' WHERE account_number = $accountNumber")
                    .await()

    suspend fun updateAccountState(accountNumber: BigDecimal): Unit? =
            client.sql("UPDATE accounts SET account_state = 'ACTIVE' WHERE account_number = $accountNumber")
                    .await()
}
