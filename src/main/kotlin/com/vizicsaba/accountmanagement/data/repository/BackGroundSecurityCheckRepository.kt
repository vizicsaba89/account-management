package com.vizicsaba.accountmanagement.data.repository

import com.vizicsaba.accountmanagement.configuration.AppConfiguration
import com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck.BackGroundSecurityCheckRequest
import com.vizicsaba.accountmanagement.data.model.backgroundsecuritycheck.BackGroundSecurityCheckResponse
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Repository
class BackGroundSecurityCheckRepository(private val webClient: WebClient, val appConfiguration: AppConfiguration) {

    suspend fun getBackGroundSecurityCheckResult(backGroundSecurityCheckRequest: BackGroundSecurityCheckRequest): BackGroundSecurityCheckResponse =
            webClient.post()
                    .uri(appConfiguration.backGroundSecurityCheckUrl)
                    .bodyValue(backGroundSecurityCheckRequest)
                    .retrieve()
                    .awaitBody<BackGroundSecurityCheckResponse>()
}