package com.ten.ware.redis.redic.strategy;

/**
 * 指明使用哪种策略来选择读写分离的从节点
 */
public interface SelectStrategy {
    /**
     * 定义如何从多个节点中选择一个从节点
     */
    int select(int count);
}
