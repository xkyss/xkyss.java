package com.xkyss.vertx;

import io.vertx.core.Vertx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CoreTest {
    Vertx vertx = Vertx.vertx();

    @Test
    public void test_timer() {
        CountDownLatch latch = new CountDownLatch(3);
        long timerId = vertx.setPeriodic(1000, handler -> {
            System.out.println("Hello");
            latch.countDown();  // 每次定时器执行时减少计数器
        });
        Assertions.assertTrue(timerId >= 0);

        // 等待计数器归零，最多等待10秒
        try {
            boolean completed = latch.await(10, TimeUnit.SECONDS);
            Assertions.assertTrue(completed, "Timer did not execute as expected");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
