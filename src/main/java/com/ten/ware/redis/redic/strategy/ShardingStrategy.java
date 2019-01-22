package com.ten.ware.redis.redic.strategy;

/**
 * 路由规则
 */
public interface ShardingStrategy {
    /**
     * 定义了从关键字映射缓存分片节点的方法
     */
    <T> int key2node(T key, int nodeCount);
}
