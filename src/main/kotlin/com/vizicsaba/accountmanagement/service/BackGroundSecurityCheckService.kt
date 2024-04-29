package com.vizicsaba.accountmanagement.service

import com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck.BackGroundSecurityCheckRequest
import com.vizicsaba.accountmanagement.data.repository.BackGroundSecurityCheckRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class BackGroundSecurityCheckService(private val backGroundSecurityCheckRepository: BackGroundSecurityCheckRepository) {

    suspend fun getBackGroundSecurityCheckResult(backGroundSecurityCheckRequest: BackGroundSecurityCheckRequest) {
        GlobalScope.launch {
            backGroundSecurityCheckRepository.getBackGroundSecurityCheckResult(backGroundSecurityCheckRequest)
        }
    }
}