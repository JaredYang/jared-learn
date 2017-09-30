package com.jared.core.http.apache.async;

/**
 * Created by yangjunde on 2017/9/26.
 */
import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * This example demonstrates a basic asynchronous HTTP request / response exchange
 * via an HTTP proxy.
 */
public class AsyncClientExecuteProxy {

    public static void main(String[] args)throws Exception {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            httpclient.start();
            HttpHost proxy = new HttpHost("localhost", 8888);
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpGet request = new HttpGet("https://httpbin.org/");
            request.setConfig(config);
            Future<HttpResponse> future = httpclient.execute(request, null);
            HttpResponse response = future.get();
            System.out.println("Response: " + response.getStatusLine());
            System.out.println("Shutting down");
        } finally {
            httpclient.close();
        }
    }

}
