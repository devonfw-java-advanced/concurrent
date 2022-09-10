package com.devonfw.java.training.concurrent.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MathService {

    @Async
    public CompletableFuture<Double> multiplyAsync(Double a, Double b) throws InterruptedException {
        return CompletableFuture.completedFuture(multiply(a, b));
    }

    public Double multiply(Double a, Double b) throws InterruptedException {
        // multiply takes some time
        for (int i = 0; i < 3 && !Thread.currentThread().isInterrupted(); i++) {
            Thread.sleep(1000);
        }
        return a * b;
    }

}
