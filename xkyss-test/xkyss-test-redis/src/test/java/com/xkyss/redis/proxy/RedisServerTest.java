package com.xkyss.redis.proxy;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class RedisServerTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisServerTest.class);

    Vertx vertx = Vertx.vertx();

    @Test
    public void listenTest() throws InterruptedException {
        RedisServerOptions options = new RedisServerOptions()
            .setHost("localhost")
            .setPort(6479);
        RedisServer server = RedisServer.create(this.vertx, options);
        server.endpointHandler(endpoint -> {
            logger.info("endpoint: {}", endpoint);
        });

        VertxTestContext testContext = new VertxTestContext();
        server.listen()
            .onFailure(t -> {
                logger.error("listen FAILED.", t);
                testContext.failNow(t);
            })
            .onComplete(ar -> {
                testContext.succeedingThenComplete();
            });

        Assertions.assertTrue(testContext.awaitCompletion(200, TimeUnit.SECONDS));
    }
}
