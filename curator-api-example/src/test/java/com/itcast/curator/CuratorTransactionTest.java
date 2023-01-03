package com.itcast.curator;

import org.junit.Test;

public class CuratorTransactionTest extends BaseTest {

    public CuratorTransactionTest() {
        super("create2");
    }

    @Test
    public void trans1() throws Exception {
//        client.create().forPath("/node1", "node1".getBytes());
//        client.setData().forPath("/node2", "node2".getBytes());
        // 开启事务，commit() 事务的提交
        // Node2不成功，则Node1也不会提交成功
        client.inTransaction()
                .create().forPath("/node1", "node1".getBytes())
                .and()
                .setData().forPath("/node2", "node2".getBytes())
                .and()
                .commit();
    }

    @Test
    public void trans2() throws Exception {
//        client.create().forPath("/node1", "node1".getBytes());
//        client.setData().forPath("/node2", "node2".getBytes());
        // 开启事务，commit() 事务的提交
        // Node2不成功，则Node1也不会提交成功
        client.inTransaction()
                .create().forPath("/node1", "node1".getBytes())
                .and()
                .create().forPath("/node2", "node2".getBytes())
                .and()
                .commit();
    }

}
