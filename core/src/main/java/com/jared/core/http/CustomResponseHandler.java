package com.jared.core.http;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by yangjunde on 2017/9/22.
 */
public class CustomResponseHandler extends AbstractResponseHandler
{

    @Override
    public String handleEntity(HttpEntity entity) throws IOException {

        return entity != null ?  EntityUtils.toString(entity) : null;
    }


}
