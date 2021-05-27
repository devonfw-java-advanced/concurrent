package com.devonfw.java.training.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.devonfw.java.training.concurrent.service.MathService;
import com.devonfw.java.training.concurrent.service.PiService;
import com.devonfw.java.training.concurrent.service.PrinterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcurrentApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(ConcurrentApplication.class);

    @Autowired
    private Executor executor;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private PiService piService;

    @Autowired
    private MathService mathService;

    @Autowired
    private PrinterService printerService;

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException, ExecutionException {
        // *** run work in background

        // future
        Future<Pi> futurePi = executorService.submit(new Callable<Pi>() {
            public Pi call() {
                return piService.computePi(6);
            }
        });
        logger.info("futurePi prepared");

        // future task based on callable
        FutureTask<Pi> futureTaskPi1 = new FutureTask<>(new Callable<Pi>() {
            @Override
            public Pi call() throws Exception {
                return piService.computePi(5);
            }
        });
        logger.info("futureTaskPi1 prepared");

        executor.execute(futureTaskPi1);
        logger.info("futureTaskPi1 execution started");

        // future task based on async
        Future<Pi> futureTaskPi2 = piService.computePiAsyncFuture(7);
        logger.info("futureTaskPi2 prepared");

        // *** main thread
        int timeInMainThraed = 10;
        for (int i = 1; i <= timeInMainThraed; i++) {
            logger.info("Do something in main thread {}/{}", i, timeInMainThraed);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        // *** get results from work run in background
        logger.info("pi from futurePi: {}", futurePi.get().getComputedPi());
        logger.info("pi from futureTaskPi1: {}", futureTaskPi1.get().getComputedPi());
        logger.info("pi from futureTaskPi2: {}", futureTaskPi2.get().getComputedPi());

        // *** final cleanup
        executorService.shutdown();
    }

}
