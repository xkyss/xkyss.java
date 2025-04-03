package com.xkyss.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class EventBusTest {
    Vertx vertx = Vertx.vertx();

    @Test
    public void test() throws InterruptedException {
        VertxTestContext testContext = new VertxTestContext();
        AtomicInteger totalCount = new AtomicInteger();

        vertx.eventBus()
            .consumer("s", msg -> {
                System.out.println("received: " + msg.body());
                if (totalCount.incrementAndGet() == 2) {
                    testContext.completeNow();
                }
            });

        vertx.eventBus().send("s", "hello");
        vertx.eventBus().send("s", JsonObject.of("a", 11));

        testContext.awaitCompletion(5, TimeUnit.SECONDS);
    }

    @Test
    public void test2() throws InterruptedException {
        VertxTestContext testContext = new VertxTestContext();
        vertx.eventBus()
            .consumer("s", msg -> {
                System.out.println("received: " + msg.body());
                testContext.completeNow();
            });

        // 不能直接发送对象，会报错
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            vertx.eventBus().send("s", new Foo().name("foo"));
        });

        // 可以发送json对象
        JsonObject jo = JsonObject.mapFrom(new Foo().name("foo"));
        vertx.eventBus().send("s", jo);

        testContext.awaitCompletion(1, TimeUnit.SECONDS);
    }

    static class Foo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Foo name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "Foo{" +
                "name='" + name + '\'' +
                '}';
        }
    }
}
