package com.jared.core.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-21
 * Time: 下午9:26
 * To change this template use File | Settings | File Templates.
 */
public class TestZookeeper {
    private static String server = "42.121.136.60:2181";
    private static final int sessionTimeOut = 30000;

    public static void main(String[] agrs) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(server, sessionTimeOut, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("已经触发了" + event.getType() + "事件！");
                }
            });
            zooKeeper.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.create("/testRootPath/testChildPathOne", "testChildPathOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(new String(zooKeeper.getData("/testRootPath", false, null)));
            System.out.println(zooKeeper.getChildren("/testRootPath", true));
            zooKeeper.setData("/testRootPath/testChildPathOne", "testChangedPathOne".getBytes(), -1);
            System.out.println(String.valueOf(zooKeeper.exists("/testRootPath", true)));
            zooKeeper.create("/testRootPath/testChildPathTwo", "testChildPathTwo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(zooKeeper.getChildren("/testRootPath", true));
            zooKeeper.delete("/testRootPath/testChildPathOne", -1);
            zooKeeper.delete("/testRootPath/testChildPathTwo", -1);
            zooKeeper.delete("/testRootPath", -1);
            zooKeeper.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
