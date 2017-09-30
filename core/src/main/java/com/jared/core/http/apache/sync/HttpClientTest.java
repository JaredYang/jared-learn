package com.jared.core.http.apache.sync;

import com.jared.core.http.apache.sync.CustomResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;


/**
 * Created by yangjunde on 2017/9/22.
 * httpccomponents 4.5.3
 *
 * http://hc.apache.org/httpcomponents-client-ga/examples.html
 */
public class HttpClientTest {

    public static void main(String[] args) throws Exception{
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("www.baidu.com")
                .setPath("s")
                .setParameter("wd","alibaba")
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String res = (String) httpClient.execute(httpGet,new CustomResponseHandler());
        System.out.println(res);
    }

}
;