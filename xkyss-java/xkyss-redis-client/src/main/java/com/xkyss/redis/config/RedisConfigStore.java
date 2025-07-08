package com.xkyss.redis.config;

import com.xkyss.redis.client.Command;
import com.xkyss.redis.client.Redis;
import com.xkyss.redis.client.RedisOptions;
import com.xkyss.redis.client.Request;
import io.vertx.config.spi.ConfigStore;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class RedisConfigStore implements ConfigStore {
    private final Redis redis;
    private final String field;

    public RedisConfigStore(Vertx vertx, JsonObject config) {
        this.field = config.getString("key", "sider-config");
        this.redis = Redis.createClient(vertx, new RedisOptions(config));
    }

    @Override
    public Future<Void> close() {
        redis.close();
        return Future.succeededFuture();
    }

    @Override
    public Future<Buffer> get() {
        return redis.send(Request.cmd(Command.HGETALL).arg(field))
                .map(resp -> {
                    JsonObject result = new JsonObject();
                    for (String key : resp.getKeys()) {
                        result.put(key, resp.get(key).toString());
                    }
                    return result.toBuffer();
                });
    }
}
