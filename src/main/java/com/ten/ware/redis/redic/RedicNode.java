package com.ten.ware.redis.redic;

import java.util.ArrayList;
import java.util.List;

import com.ten.ware.redis.redic.strategy.SelectStrategy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisPool;

import com.ten.ware.redis.redic.strategy.RoundRobinSelectStrategy;

/**
 * 主从节点数据
 */
public class RedicNode {
    /**
     * 主从节点分隔符
     */
    public static final String NODE_SEPARATOR = ",";

    /**
     * 分隔符
     */
    public static final String HOST_PORT_SEPARATOR = ":";

    /**
     * 主节点：写
     */
    private JedisPool master;
    /**
     * 从节点列表：读
     */
    private List<JedisPool> slaves;

    /**
     * 从节点选择策略
     */
    public SelectStrategy selectStrategy;

    public RedicNode(JedisPool master, List<JedisPool> slaves) {
        this.master = master;
        this.slaves = slaves;
        this.selectStrategy = new RoundRobinSelectStrategy();
    }

    public RedicNode(JedisPool master, List<JedisPool> slaves,
                     SelectStrategy selectStrategy) {
        this.master = master;
        this.slaves = slaves;
        this.selectStrategy = selectStrategy;
    }

    /**
     * 根据配置文件传入的节点路径信息生成节点对象
     */
    public RedicNode(String masterConnStr, List<String> slavesConnStrs) {
        // 根据IP:PORT获取主节点JedisPool连接池
        String[] masterHostPortArray = masterConnStr.split(HOST_PORT_SEPARATOR);
        this.master = new JedisPool(new GenericObjectPoolConfig(), masterHostPortArray[0], Integer.valueOf(masterHostPortArray[1]));

        // 根据IP:PORT获取从节点JedisPool连接池
        this.slaves = new ArrayList<>();
        for (String slaveConnStr : slavesConnStrs) {
            String[] slaveHostPortArray = slaveConnStr.split(HOST_PORT_SEPARATOR);
            this.slaves.add(new JedisPool(new GenericObjectPoolConfig(), slaveHostPortArray[0], Integer.valueOf(slaveHostPortArray[1])));
        }

        this.selectStrategy = new RoundRobinSelectStrategy();
    }

    public RedicNode(String masterConnStr, List<String> slavesConnStrs,
                     SelectStrategy selectStrategy) {
        this(masterConnStr, slavesConnStrs);
        this.selectStrategy = selectStrategy;
    }

    public JedisPool getMaster() {
        return master;
    }

    public void setMaster(JedisPool master) {
        this.master = master;
    }

    public List<JedisPool> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<JedisPool> slaves) {
        this.slaves = slaves;
    }

    public JedisPool getRoundRobinSlaveRedicNode() {
        int nodeIndex = selectStrategy.select(slaves.size());

        return slaves.get(nodeIndex);
    }
}
