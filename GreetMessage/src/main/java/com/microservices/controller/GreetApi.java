package com.microservices.controller;
import com.microservices.client.WelcomeFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetApi {

    @Value("${msg}")
    private String message;

    @GetMapping("/")
    public String getMsg(){
        return message;
    }
    private static final Logger log = LoggerFactory.getLogger(GreetApi.class);

    @Autowired
    private WelcomeFeignClient welcomeFeignClient;

    @GetMapping("/greet")
    @CircuitBreaker(name = "welcomeBreak", fallbackMethod = "welcomeFallback")
    @Retry(name = "welcomeRetry",fallbackMethod = "welcomeFallback")
    public String getGreetMsg() {
        log.info("Greet Controller.....");
        String welcomeMsg = welcomeFeignClient.getWelcomeMsg();
        return "Good Morning, " + welcomeMsg;
    }

    // Fallback method
    public String welcomeFallback(Throwable throwable) {
        log.info("Fallback method called due to: {}", throwable.getMessage());
        return "Fallback: Service is currently unavailable.";
    }
}

