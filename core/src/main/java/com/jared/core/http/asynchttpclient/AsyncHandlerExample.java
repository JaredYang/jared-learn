package com.jared.core.http.asynchttpclient;

import org.asynchttpclient.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yangjunde on 2017/9/26.
 */
public class AsyncHandlerExample {

    public static void main(String[] args) {
        try {
            AsyncHttpClient client = new DefaultAsyncHttpClient();
            Future<Response> f = client.prepareGet("http://www.baidu.com").execute(new AsyncHandler<Response>() {
                @Override
                public void onThrowable(Throwable t) {

                }

                @Override
                public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                    return null;
                }

                @Override
                public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                    return null;
                }

                @Override
                public State onHeadersReceived(HttpResponseHeaders headers) throws Exception {
                    return null;
                }

                @Override
                public Response onCompleted() throws Exception {
                    return null;
                }
            });
            Response response = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
