package com.bingo.springboot.autoconfigure;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * @author Bingo
 * @title: MyHealthIndicator
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/18  23:19
 */
@Component("bingo")
public class MyHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.withDetail("name", "bingo").withDetail("date", System.currentTimeMillis()).up().build();
    }
}
