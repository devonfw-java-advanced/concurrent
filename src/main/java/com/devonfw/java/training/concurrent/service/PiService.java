package com.devonfw.java.training.concurrent.service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.devonfw.java.training.concurrent.entity.Pi;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class PiService {

    @Async
    public CompletableFuture<Pi> computePiAsync(int timeToComputeInSeconds) {
        Pi pi = computePi(timeToComputeInSeconds);
        return CompletableFuture.completedFuture(pi);
    }

    @Async
    public Future<Pi> computePiAsyncFuture(int timeToComputeInSeconds) {
        return new AsyncResult<Pi>(computePi(timeToComputeInSeconds));
    }

    public Pi computePi(int timeToComputeInSeconds) {
        long nThrows = 0;
        long nHits = 0;

        Instant now = Instant.now();
        Instant end = now.plusSeconds(timeToComputeInSeconds);
        while (end.isAfter(now) && !Thread.currentThread().isInterrupted()) {
            // trow dart and count
            nThrows++;
            if (whetherTheDartHit()) {
                nHits++;
            }

            now = Instant.now();
        }

        if (Thread.currentThread().isInterrupted()) {
            return null;
        }

        Pi pi = computePiUsingThrowsAndHits(nThrows, nHits);

        return pi;
    }

    private boolean whetherTheDartHit() {
        // throwing a dart takes some time
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }

        double x = Math.random(), y = Math.random();
        return x * x + y * y <= 1.0;
    }

    private Pi computePiUsingThrowsAndHits(long nThrows, long nSuccess) {
        double computedPi = 4 * (double) nSuccess / (double) nThrows;
        double error = Math.abs(Math.PI - computedPi);
        return new Pi(computedPi, error);
    }
}
