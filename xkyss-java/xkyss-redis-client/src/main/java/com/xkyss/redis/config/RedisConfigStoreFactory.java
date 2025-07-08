package com.xkyss.redis.config;

import io.vertx.config.spi.ConfigStore;
import io.vertx.config.spi.ConfigStoreFactory;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class RedisConfigStoreFactory implements ConfigStoreFactory {
    @Override
    public String name() {
        return "xkyss-redis";
    }

    @Override
    public ConfigStore create(Vertx vertx, JsonObject jsonObject) {
        return new RedisConfigStore(vertx, jsonObject);
    }
}

