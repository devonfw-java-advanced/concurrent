package com.devonfw.java.training.concurrent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PrinterService {

    private Logger logger = LoggerFactory.getLogger(PrinterService.class);

    @Async
    public void printAsync(Double result) throws InterruptedException {
        print(result);
    }

    public void print(Double result) throws InterruptedException {
        // printing takes some time
        for (int i = 0; i < 2 && !Thread.currentThread().isInterrupted(); i++) {
            Thread.sleep(1000);
        }
        logger.info("result: {}", result);
    }

}
