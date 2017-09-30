package com.jared.core.http.apache.async;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yangjunde on 2017/9/26.
 *
 * http://hc.apache.org/httpcomponents-asyncclient-4.1.x/index.html
 * http://hc.apache.org/httpcomponents-asyncclient-4.1.x/examples.html
 */
public class AsyncHttpClientExample {

    public static void main(String[] args) throws Exception{
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        try {
            client.start();
            HttpGet httpGet = new HttpGet("www.baidu.com");
            Future<HttpResponse> future = client.execute(httpGet,null);
            HttpResponse response = future.get();
            StatusLine statusLine = response.getStatusLine();
            System.out.println(statusLine);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }


    }

}
