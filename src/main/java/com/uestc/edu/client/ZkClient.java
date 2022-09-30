package com.uestc.edu.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @ClassName: ZkClient.java
 * @Description: zk 客户端
 * @Author: linchen8@lenovo.com
 * @Date: 2022/9/30 11:17
 **/
public class ZkClient {

    public static CuratorFramework getClient() {
        return CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                //连接超时时间，默认15秒
                .connectionTimeoutMs(15 * 1000)
                //会话超时时间，默认60秒
                .sessionTimeoutMs(60 * 1000)
                //设置命名空间
                .namespace("arch")
                .build();
    }

    public static void main(String[] args) {
        CuratorFramework client = getClient();
        System.out.println(client.toString());
    }

}
