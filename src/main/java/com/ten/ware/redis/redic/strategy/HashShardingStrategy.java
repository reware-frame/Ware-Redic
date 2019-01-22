package com.ten.ware.redis.redic.strategy;

/**
 * 路由规则
 */
public class HashShardingStrategy implements ShardingStrategy {
    /**
     * 默认使用关键字哈希的路由规则
     */
    @Override
    public <T> int key2node(T key, int nodeCount) {
        int hashCode = key.hashCode();
        return hashCode % nodeCount;
    }
}
