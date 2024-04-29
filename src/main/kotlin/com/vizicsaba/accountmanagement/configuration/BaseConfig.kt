package com.vizicsaba.accountmanagement.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
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
        val strategies = ExchangeStrategies
                .builder()
                .codecs { clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(jacksonObjectMapper(), MediaType.APPLICATION_JSON))
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(jacksonObjectMapper(), MediaType.APPLICATION_JSON))
                }.build()

        return builder.exchangeStrategies(strategies).build()
    }
}
