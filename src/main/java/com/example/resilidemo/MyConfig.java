package com.example.resilidemo;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class MyConfig {

    public static final String RETRY_NAME = "backendA";
    public static final String CIRCUIT_BREAKER_NAME = "backendA";

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private final RetryRegistry retryRegistry;

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .failureRateThreshold(50)
                //.slowCallDurationThreshold()
                //.maxWaitDurationInHalfOpenState(Duration.ofSeconds(10))
                //.slowCallRateThreshold()
                //.minimumNumberOfCalls(5)
                //.permittedNumberOfCallsInHalfOpenState(5)
                //.recordExceptions()
                //.ignoreExceptions()
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                //.writableStackTraceEnabled(true)
                //.recordExceptionPredicate()
                //.ignoreExceptionPredicate()
                //.currentTimestampFunction()
                //.timestampUnit()
                //.recordResultPredicate()
                .waitDurationInOpenState(Duration.ofMillis(15000))
                //.permittedNumberOfCallsInHalfOpenState(2)
                //.recordExceptions(IOException.class, TimeoutException.class)
                //.ignoreExceptions(BusinessException.class, OtherBusinessException.class)
                .build();
    }

    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(200))
                .build();
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerConfig circuitBreakerConfig) {
        return circuitBreakerRegistry
                .circuitBreaker(CIRCUIT_BREAKER_NAME, circuitBreakerConfig);
    }

    @Bean
    public Retry retry(RetryConfig retryConfig) {
        return retryRegistry.retry(RETRY_NAME, retryConfig);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
