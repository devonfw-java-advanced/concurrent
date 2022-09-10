package com.devonfw.java.training.concurrent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PrinterService {

    private Logger logger = LoggerFactory.getLogger(PrinterService.class);

    @Async
    public void printAsync(Double result) {
        print(result);
    }

    public void print(Double result) {
        Instant now = Instant.now();
        Instant end = now.plusSeconds(2);
        while (end.isAfter(now) && !Thread.currentThread().isInterrupted()) {
            // printing takes some time
            now = Instant.now();
        }
        logger.info("result: {}", result);
    }

}
