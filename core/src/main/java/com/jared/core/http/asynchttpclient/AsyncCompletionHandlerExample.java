package com.jared.core.http.asynchttpclient;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

/**
 * Created by yangjunde on 2017/9/26.
 */
public class AsyncCompletionHandlerExample {

    public static void main(String[] args) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepareGet("www.baidu.com").execute(new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) throws Exception {
                return null;
            }
        });
    }
}
