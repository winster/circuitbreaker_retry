# Circuit Breaker with Retry Configuration


### Things to notice

* MyService::backendA will be retried a maximum of 3 attempts on any exception
* Circuit will open after a minimum number of calls which is 5
* Circuit will stay in open state for a maximum of 15 seconds

* **Check the fallback method signatures carefully** to see how *RETRY* and *CircuitBreaker* are used together.

### How to test

* Prerequisite - make *MyService::backendA* to throw an exception


* curl http://localhost:8888/api        [Circuit is *CLOSED*]
    * Confirm that *MyService::backendA* is called 3 times before invoking *MyService::fallbackRetry*
* curl http://localhost:8888/api        [Circuit is *CLOSED*]
    * Confirm that *MyService::backendA* is called 2 times before invoking *MyService::fallbackCircuitBreaker*
* curl http://localhost:8888/api        [Circuit is *OPEN* now]
    * Confirm that *MyService::backendA* is **not called** instead *MyService::fallbackCircuitBreaker* is directly called

Let circuit breaker return to *CLOSED* state after 15 seconds. If you rerun CURL requests again,
you should see the expected output remains the same.


### Reference Documentation

For further reference, please consider the following sections:

* [Resilience4J documentation](https://resilience4j.readme.io/docs)
* [A related github issue about using the features together](https://github.com/resilience4j/resilience4j/issues/558)
