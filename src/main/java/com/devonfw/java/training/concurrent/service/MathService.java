package com.devonfw.java.training.concurrent.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    @Async
    public CompletableFuture<Double> multiplyAsync(Double a, Double b) {
        return CompletableFuture.completedFuture(multiply(a, b));
    }

    public Double multiply(Double a, Double b) {
        // multiply takes some time
        for (int i = 0; i < 3 && !Thread.currentThread().isInterrupted(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }
        return a * b;
    }

}
