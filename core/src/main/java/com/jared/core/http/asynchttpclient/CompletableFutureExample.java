package com.jared.core.http.asynchttpclient;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

/**
 * Created by yangjunde on 2017/9/26.
 */
public class CompletableFutureExample {

    public static void main(String[] args) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
//        client.prepareGet("http://www.baidu.com")
//              .execute()
//              .toCompletableFuture()
//                .thenApply(Response :: getResponseBody)
//                .thenAccept(System.out::println)
//                .join();
    }
}
