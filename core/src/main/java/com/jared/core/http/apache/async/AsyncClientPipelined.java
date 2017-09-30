package com.jared.core.http.apache.async;

/**
 * Created by yangjunde on 2017/9/26.
 */
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpPipeliningClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * This example demonstrates a pipelinfed execution of multiple HTTP request / response exchanges
 * Response content is buffered in memory for simplicity.
 */
public class AsyncClientPipelined {

    public static void main(final String[] args) throws Exception {
        CloseableHttpPipeliningClient httpclient = HttpAsyncClients.createPipelining();
        try {
            httpclient.start();

            HttpHost targetHost = new HttpHost("httpbin.org", 80);
            HttpGet[] resquests = {
                    new HttpGet("/"),
                    new HttpGet("/ip"),
                    new HttpGet("/headers"),
                    new HttpGet("/get")
            };

            Future<List<HttpResponse>> future = httpclient.execute(targetHost,
                    Arrays.<HttpRequest>asList(resquests), null);
            List<HttpResponse> responses = future.get();
            for (HttpResponse response: responses) {
                System.out.println(response.getStatusLine());
            }
            System.out.println("Shutting down");
        } finally {
            httpclient.close();
        }
        System.out.println("Done");
    }

}