package com.jared.core.zookeeper;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * TestMainClient
 * <p/>
 * Author By: junshan
 * Created Date: 2010-9-7 14:11:44
 * http://rdc.taobao.com/team/jm/archives/1232
 * http://iwinit.iteye.com/blog/1754123
 */
public class TestMainClient implements Watcher {
    protected static ZooKeeper zk = null;
    protected static Integer mutex;
    int sessionTimeout = 10000;
    protected String root;
    

    

    public TestMainClient(String connectString) {
        if(zk == null){
            try {

                String configFile = this.getClass().getResource("/").getPath()+"net/xulingbo/zookeeper/log4j/log4j.xml";
                DOMConfigurator.configure(configFile);
                System.out.println("");
                zk = new ZooKeeper(connectString, sessionTimeout, this);
                mutex = new Integer(-1);
            } catch (IOException e) {
                zk = null;
            }
        }
    }
    synchronized public void process(WatchedEvent event) {
        synchronized (mutex) {
            mutex.notify();
        }
    }
}
