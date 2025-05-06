package com.xkyss.vertx.vo;


import java.util.List;

public class CacheWithManagerBatchPatchReq {
    private Long expire;

    private List<CacheKey> keys;

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public List<CacheKey> getKeys() {
        return keys;
    }

    public void setKeys(List<CacheKey> keys) {
        this.keys = keys;
    }
}
