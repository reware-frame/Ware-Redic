package com.ten.ware.redis.router.strategy;

public interface ShardingStrategy {
	public <T> int key2node(T key, int nodeCount);
}
