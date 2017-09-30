package com.jared.core.http.apache.async;

/**
 * Created by yangjunde on 2017/9/26.
 */
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContexts;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class AsyncClientCustomSSL {

    public final static void main(String[] args) throws Exception {
        // Trust standard CAs and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();
        // Allow TLSv1 protocol only
        SSLIOSessionStrategy sslSessionStrategy = new SSLIOSessionStrategy(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLIOSessionStrategy.getDefaultHostnameVerifier());
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
                .setSSLStrategy(sslSessionStrategy)
                .build();
        try {
            httpclient.start();
            HttpGet httpget = new HttpGet("https://httpbin.org/");
            Future<HttpResponse> future = httpclient.execute(httpget, null);
            HttpResponse response = future.get();
            System.out.println("Response: " + response.getStatusLine());
            System.out.println("Shutting down");
        } finally {
            httpclient.close();
        }
        System.out.println("Done");
    }

}
