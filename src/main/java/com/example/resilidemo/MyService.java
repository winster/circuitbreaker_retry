package com.example.resilidemo;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.resilidemo.MyConfig.CIRCUIT_BREAKER_NAME;
import static com.example.resilidemo.MyConfig.RETRY_NAME;

@Service
@Slf4j
@AllArgsConstructor
public class MyService {

    static final String BACKEND_URL = "http://localhost:9090/serviceA";
    static final String FALLBACK_URL = "http://localhost:9091/serviceB";

    static final String RETRY_FALLBACK_METHOD = "fallbackRetry";
    static final String CIRCUIT_BREAKER_FALLBACK_METHOD = "fallbackCircuitBreaker";

    private final RestTemplate restTemplate;

    @Retry(name = RETRY_NAME, fallbackMethod = RETRY_FALLBACK_METHOD)
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = CIRCUIT_BREAKER_FALLBACK_METHOD)
    public String backendA() {
        log.info("inside backendA");
        String result = restTemplate.getForObject(BACKEND_URL, String.class);
        log.info("backendA : result {}", result);
        return result;
    }

    public String fallbackRetry(Exception exception) {
        String result = restTemplate.getForObject(FALLBACK_URL, String.class);
        log.info("fallbackRetry : result {}", result);
        return result;
    }

    public String fallbackCircuitBreaker(CallNotPermittedException callNotPermittedException) {
        String result = restTemplate.getForObject(FALLBACK_URL, String.class);
        log.info("fallbackCircuitBreaker : result {}", result);
        return result;
    }
}
