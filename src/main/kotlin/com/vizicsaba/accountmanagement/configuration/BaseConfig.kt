package com.vizicsaba.accountmanagement.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(AppConfiguration::class)
class BaseConfig {

    @Bean
    fun appConfiguration(): AppConfiguration {
        return AppConfiguration()
    }

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder.build()
    }
}
