package com.devonfw.java.training.concurrent.entity;

import com.devonfw.java.training.concurrent.logging.FunctionLoggingAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pi {

    Logger logger = LoggerFactory.getLogger(Pi.class);

    private Double computedPi;
    private Double error;

    public Pi(Double computedPi, Double error) {
        this.computedPi = computedPi;
        this.error = error;
    }

    public Double getError() {
        return error;
    }

    public Double getComputedPi() {
        logger.info("Call Pi.getComputedPi(): {}", computedPi);

        return computedPi;
    }

}
