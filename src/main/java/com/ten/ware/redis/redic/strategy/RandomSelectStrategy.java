package com.ten.ware.redis.redic.strategy;

import java.util.Random;

public class RandomSelectStrategy implements SelectStrategy {
    private Random random = new Random(System.currentTimeMillis());

    /**
     * 随机选择
     */
    @Override
    public int select(int count) {
        return random.nextInt(count);
    }
}
