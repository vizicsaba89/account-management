package com.vizicsaba.accountmanagement.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class AppConfiguration(
        var bankIdentifier: Long = 0,
        var callbackUrl: String = "",
        var backGroundSecurityCheckUrl: String = ""
)
