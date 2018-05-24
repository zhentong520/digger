package com.ko30.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * 
* @ClassName: HttpClientUtil 
* @Description: 实现url请求工具类 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月21日 下午12:29:17 
*
 */
public class HttpClientUtil {
	private static final transient Logger log = Logger.getLogger(HttpClientUtil.class);
	private static MultiThreadedHttpConnectionManager httpClientManager = new MultiThreadedHttpConnectionManager();

	static {
		httpClientManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = httpClientManager.getParams();
		params.setStaleCheckingEnabled(true);
		params.setMaxTotalConnections(200);
		params.setDefaultMaxConnectionsPerHost(80);
		//params.setConnectionTimeout(20000);
		//params.setSoTimeout(20000);
		//httpClient.getParams().setParameter("http.socket.timeout", new Integer(30000));
		params.setLongParameter("http.socket.timeout", new Long(30000));
		params.setConnectionTimeout(30000);// 设置连接超时
		params.setSoTimeout(50000);// 设置等待结果超时
	}

	public static HttpClient getHttpClient() {
		return new HttpClient(httpClientManager);
	}

	/**
	 * 
	* @Title: getResponseHeaderByName 
	* @Description: 根据指定名称 ，获取响应头的值 
	* @param @param url
	* @param @param header
	* @param @param headerName
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getResponseHeaderByName(String url,
			Map<String, String> header, String headerName) {
		GetMethod method = null;
		try {
			HostConfiguration config = new HostConfiguration();
			URL _url = new URL(url);
			int port = _url.getPort() > 0 ? _url.getPort() : 80;
			config.setHost(_url.getHost(), port, _url.getProtocol());
			method = new GetMethod(url);
			if (header != null) {
				for (String key : header.keySet()) {
					method.addRequestHeader(key, (String) header.get(key));
				}
			}
			getHttpClient().executeMethod(config, method);
			int statusCode = method.getStatusCode();
			if (statusCode != 200) {
				log.error("statusCode:" + statusCode + ". url:" + url);
			} else {
				return method.getResponseHeader(headerName).getValue();
			}
		} catch (Exception ex) {
			log.error("error:" + ex.getMessage());
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		if (method != null) {
			method.releaseConnection();
		}
		return null;
	}

	
	/**
	 * 
	* @Title: get 
	* @Description: 以get方式请求指定地址，并以字符串形式返回请求结果
	* @param @param url
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String get(String url) {
		GetMethod method = null;
		try {
			HostConfiguration config = new HostConfiguration();
			URL _url = new URL(url);
			int port = _url.getPort() > 0 ? _url.getPort() : 80;
			config.setHost(_url.getHost(), port, _url.getProtocol());
			method = new GetMethod(url);
			getHttpClient().executeMethod(config, method);
			int statusCode = method.getStatusCode();
			if (statusCode != 200) {
				log.error("statusCode:" + statusCode + ". url:" + url);
			}
			
			// 对于返回结果很大或无法预知的情况，就需要使用InputStream getResponseBodyAsStream()，
			// 避免byte[] getResponseBody()可能带来的内存的耗尽问题
			//String responseResult = new String(method.getResponseBody(),"utf-8");
			
			// 将得到的流转为字符串
			InputStream is = method.getResponseBodyAsStream();
			String responseResult = IOUtils.toString(is, "UTF-8");
			return responseResult;
		} catch (Exception e) {
			log.error("error:" + e);
			e.printStackTrace();
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	
	/**
	 * 
	* @Title: post 
	* @Description: 以post方式 ，无参请求指定的url 
	* @param @param url
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String post(String url) {
		return post(url, null, null);
	}
	
	/**
	 * 
	* @Title: post 
	* @Description: 以post方式 ，并且带有参数，请求指定的url 
	* @param @param url
	* @param @param postData
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String post(String url, Map<String, String> postData) {
		return post(url, postData, null);
	}
	
	public static String post(String url, Map<String, String> postData,
			String productName) {
		if ((url == null) || (url == null)) {
			return null;
		}
		PostMethod post = null;
		try {
			NameValuePair[] paramPair = (NameValuePair[]) null;
			if (postData != null) {
				paramPair = new NameValuePair[postData.size()];
				int i = 0;
				for (String key : postData.keySet()) {
					NameValuePair nameValuePair = new NameValuePair();
					nameValuePair.setName(key);
					nameValuePair.setValue((String) postData.get(key));
					paramPair[i] = nameValuePair;
					i++;
				}
			}
			URL _url = new URL(url);
			HostConfiguration config = new HostConfiguration();
			int port = _url.getPort() > 0 ? _url.getPort() : 80;
			config.setHost(_url.getHost(), port, _url.getProtocol());

			post = new PostMethod(url);
			if (StringUtils.isNotEmpty(productName)) {
				post.setRequestHeader("product", productName);
			}
			if ((paramPair != null) && (paramPair.length > 0)) {
				post.setRequestBody(paramPair);
			}
			int result = getHttpClient().executeMethod(config, post);
			if (log.isDebugEnabled()) {
				log.debug("HttpClient.executeMethod returns result = ["
						+ result + "]");
			}
			if (result != 200) {
				log.error("wrong HttpClient.executeMethod post method !");
			}
			String responseResult = post.getResponseBodyAsString();
			return responseResult;
		} catch (Exception exp) {
			log.error("error:" + exp.getMessage());
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return null;
	}

	public static String post(String url, String data) {
		PostMethod post = null;
		try {
			URL _url = new URL(url);
			HostConfiguration config = new HostConfiguration();
			int port = _url.getPort() > 0 ? _url.getPort() : 80;
			config.setHost(_url.getHost(), port, _url.getProtocol());

			post = new PostMethod(url);
			post.setRequestEntity(new StringRequestEntity(data,
					"application/x-www-form-urlencoded", "UTF-8"));

			int result = getHttpClient().executeMethod(config, post);
			if (log.isDebugEnabled()) {
				log.debug("HttpClient.executeMethod returns result = ["
						+ result + "]");
			}
			if (result != 200) {
				log.error("wrong HttpClient.executeMethod post method !");
			}
			String responseResult = post.getResponseBodyAsString();
			return responseResult;
		} catch (Exception e) {
			log.error("error:" + e.getMessage());
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return null;
	}

	public static String getResponseAsString(HttpMethodBase method) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = method.getResponseBodyAsStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is,
					"GBK"));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			log.error("error:" + e.getMessage());
		} catch (IOException ioe) {
			log.error("error:" + ioe.getMessage());
		}
		return null;
	}
}
