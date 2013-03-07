package com.jared.core.test;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class TestHttpClient {
	public static void main(String[] args) {
		
		
	}
	
	public static void testGet(){
		try {
			HttpClient client = new HttpClient();
			
			HttpMethod method = new GetMethod("http://www.baidu.com");
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				    new DefaultHttpMethodRetryHandler());
			client.executeMethod(method);
			System.out.println(method.getStatusCode());
			System.out.println(method.getResponseBodyAsString());
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public  void testPost(){
		
		try {
			String url = "http://company.dajie.com/apc/team/createTeam";
			
			HttpClient client = new HttpClient();
			HttpMethod method = new PostMethod();
			//NameValuePair data = new NameValuePair("name", "jared");
			NameValuePair[] data = {new NameValuePair("teamTitle","无敌战队"),new NameValuePair("teamIntro","快来加入无敌战队吧！")};
			method.setQueryString(data);
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || 
					statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
					    // 从头中取出转向的地址
			    Header locationHeader = method.getResponseHeader("location");
			    String location = null;
			    if (locationHeader != null) {
			     location = locationHeader.getValue();
			     System.out.println("The page was redirected to:" + location);
			    } else {
			     System.err.println("Location field value is null.");
			    }
			    return;
			}
			
			
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
