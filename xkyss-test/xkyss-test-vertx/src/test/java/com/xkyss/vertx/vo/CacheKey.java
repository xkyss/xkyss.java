package com.xkyss.vertx.vo;

/**
 * 缓存key
 */
public class CacheKey {
    /**
     * 缓存区域
     */
    protected String area;

    /**
     * 缓存名
     */
    protected String cacheName;

    /**
     * 键
     */
    protected String key;

    public CacheKey() {
    }

    public CacheKey( String area, String cacheName, String key) {
        this.area = area;
        this.cacheName = cacheName;
        this.key = key;
    }

    public boolean isValid() {
        return (area != null && !area.isEmpty())
            && (cacheName != null && !cacheName.isEmpty())
            && key != null && !key.isEmpty();
    }

    public String etcdKey() {
        return area + "/" + cacheName + "/" + key;
    }

    @Override
    public String toString() {
        return "[area=" + area + ", cacheName=" + cacheName + ", key=" + key + "]";
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
