package com.jared.core.http.asynchttpclient;

import org.asynchttpclient.*;
import org.asynchttpclient.proxy.ProxyServer;
import org.asynchttpclient.proxy.ProxyServerSelector;
import org.asynchttpclient.uri.Uri;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yangjunde on 2017/9/26.
 * https://github.com/AsyncHttpClient/async-http-client
 *
 */
public class AsyncHttpClientExample {

    public static void main(String[] args) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            Future<Response> f = client.prepareGet("http://www.baidu.com").execute();
            Response response = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void init(AsyncHttpClientConfig config, Request request){
        ProxyServerSelector selector = config.getProxyServerSelector();
        selector.select(request.getUri());
    }

}
