package com.devonfw.java.training.concurrent.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Service
public class MathService {

    @Async
    public CompletableFuture<Double> multiplyAsync(Double a, Double b) {
        return CompletableFuture.completedFuture(multiply(a, b));
    }

    public Double multiply(Double a, Double b) {

        Instant now = Instant.now();
        Instant end = now.plusSeconds(3);
        while (end.isAfter(now) && !Thread.currentThread().isInterrupted()) {
            // multiply takes some time
            now = Instant.now();
        }

        return a * b;
    }

}
