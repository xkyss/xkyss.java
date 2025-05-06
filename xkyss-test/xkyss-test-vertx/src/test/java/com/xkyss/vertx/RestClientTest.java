package com.xkyss.vertx;

import com.xkyss.vertx.vo.AjaxResult;
import com.xkyss.vertx.vo.CacheKey;
import com.xkyss.vertx.vo.CacheWithManagerBatchPatchReq;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class RestClientTest {
    @Test
    public void test_01() throws ExecutionException, InterruptedException {
        Vertx vertx = Vertx.vertx();

        AtomicInteger step = new AtomicInteger(0);
        JsonObject body = WebClient.create(vertx)
            .get(8080, "localhost", "/products/prod3568")
            .as(BodyCodec.jsonObject())
            .send()
            .onSuccess(v -> {
                Assertions.assertEquals(0, step.getAndIncrement());
            })
            .toCompletionStage()
            .toCompletableFuture()
            .get()
            .body();

        Assertions.assertEquals(1, step.getAndIncrement());

        Assertions.assertNotNull(body);
        Assertions.assertEquals("prod3568", body.getString("id"));
    }

    @Test
    public void test_02() throws ExecutionException, InterruptedException, TimeoutException {
        Vertx vertx = Vertx.vertx();

        List<Future<HttpResponse<JsonObject>>> futures = new ArrayList<>();
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("192.168.1.38", 8083));
        servers.add(new Server("192.168.1.38", 8085));

        for (Server server : servers) {
            Future<HttpResponse<JsonObject>> future = WebClient.create(vertx)
                .get(server.getHostPort(), server.getHostIp(), "/sdk/cache/local")
                .addQueryParam("area", "default")
                .addQueryParam("cacheName", "CacheDemo:Service:")
                .addQueryParam("key", "113")
                .as(BodyCodec.jsonObject())
                .send()
                .onSuccess(r -> {
                    System.out.println("  Server: " + server);
                    System.out.println("Response: " + r.body());
                })
                .onFailure(e -> System.out.println(e))
                .recover(e -> {
                    return Future.succeededFuture();
                });

            futures.add(future);
        }

        Future.all(futures).toCompletionStage().toCompletableFuture().get(5000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void test_03() throws ExecutionException, InterruptedException, TimeoutException {
        VertxTestContext testContext = new VertxTestContext();
        Vertx vertx = Vertx.vertx();
        long t1 = System.currentTimeMillis();

        CacheWithManagerBatchPatchReq req = new CacheWithManagerBatchPatchReq();
        req.setExpire(360L);
        req.setKeys(Arrays.asList(new CacheKey("default", "AR_com.thzt.mlcache.ar.po.Product", "95840197-7750-43ee-bca9-192413b78012")));
        WebClient.create(vertx)
            .post(8084, "192.168.1.38", "/sdk/cache/with-manager/batchExpire")
            .as(BodyCodec.json(AjaxResult.class))
            .sendJson(req)
            .onComplete(v -> {
                System.out.println(v.result().body());
                long t2 = System.currentTimeMillis();
                System.out.println("耗时(ms):" + (t2 - t1));
                testContext.completeNow();
            });

        testContext.awaitCompletion(5, TimeUnit.SECONDS);
    }

    @Test
    public void test_03_sync() throws ExecutionException, InterruptedException, TimeoutException {
        Vertx vertx = Vertx.vertx();
        long t1 = System.currentTimeMillis();

        try {
            CacheWithManagerBatchPatchReq req = new CacheWithManagerBatchPatchReq();
            req.setExpire(360L);
            req.setKeys(Arrays.asList(new CacheKey("default", "AR_com.thzt.mlcache.ar.po.Product", "95840197-7750-43ee-bca9-192413b78012")));
            AjaxResult body = WebClient.create(vertx)
                .post(8084, "192.168.1.38", "/sdk/cache/with-manager/batchExpire")
                .as(BodyCodec.json(AjaxResult.class))
                .sendJson(req)
                .toCompletionStage()
                .toCompletableFuture()
                .get(5, TimeUnit.SECONDS)
                .body();

            System.out.println(body);
            long t2 = System.currentTimeMillis();
            System.out.println("耗时(ms):" + (t2 - t1));

        } catch (Exception e) {
            System.out.println("修改失败");
        }

    }

    static class Server {
        private String hostIp;
        private int hostPort;

        public Server(String hostIp, int hostPort) {
            this.hostIp = hostIp;
            this.hostPort = hostPort;
        }

        @Override
        public String toString() {
            return JsonObject.of("hostIp", hostIp, "hostPort", hostPort).toString();
        }

        public String getHostIp() {
            return hostIp;
        }

        public void setHostIp(String hostIp) {
            this.hostIp = hostIp;
        }

        public int getHostPort() {
            return hostPort;
        }

        public void setHostPort(int hostPort) {
            this.hostPort = hostPort;
        }
    }
}
