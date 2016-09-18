/**
 * 
 */
package org.njqspringboot.common.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * http客户端工具类
 * 
 * @author Yu.ling
 *
 */
public class LambdaHttpClientUtils {

	private static Logger logger = LoggerFactory.getLogger(LambdaHttpClientUtils.class.getSimpleName());
	
	private final static String defaultCharset = Consts.UTF_8.displayName();
	
	private static CloseableHttpClient httpClient;
	
	

	/**
	 * 取HTTPCLIENT
	 * @return
	 */
	private synchronized static CloseableHttpClient getHttpClient() {
		if (null == httpClient) {



			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000).setSocketTimeout
					(10000).build();
			ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();


			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null,
						new TrustManager[]{new X509TrustManager() {
							public X509Certificate[] getAcceptedIssuers() {

								return null;
							}

							public void checkClientTrusted(
									X509Certificate[] certs, String authType) {

							}

							public void checkServerTrusted(
									X509Certificate[] certs, String authType) {

							}
						}}, new SecureRandom());
			} catch (Exception e) {
				logger.error("获取HTTP客户端失败", e);
			}



			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", plainsf)
					.register("https", sslsf)
					.build();


			HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
			httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
					.setConnectionManager(cm)
					.build();


		}

		return httpClient;
		
	}
	   
	/**
	 * 发送get请求
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		if(TextUtils.isEmpty(url)){
			logger.debug("Request URL is empty.");
			return null;
		}

		String retstr = null;
		//组装查询串
		if (null != params && !params.isEmpty()) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
			Set<Entry<String, String>> set = params.entrySet();
			for (Entry<String, String> entry : set) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			String queryStr = EntityUtils.toString(new UrlEncodedFormEntity(pairs), defaultCharset);
			if(!TextUtils.isEmpty(queryStr)){
				if(url.contains("?")){
					url += "&" + queryStr;
				}else{					
					url += "?" + queryStr;
				}
			}
		}
		logger.debug("http reqeust = " + url);
		HttpGet get = new HttpGet(url);
		//addHeader(get);
		
		try (CloseableHttpResponse response = getHttpClient().execute(get)) {
			HttpEntity entity = response.getEntity();
			if(null != entity){				
				retstr = EntityUtils.toString(entity, defaultCharset);
				logger.debug("http response = " + retstr);
			}
			EntityUtils.consume(entity);
		}
		return retstr;
	}
	
	/**
	 * 发请post请求
	 * @param url
	 * @param jsonMsg
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, String jsonMsg) throws ClientProtocolException, IOException{
		if(TextUtils.isEmpty(url)){
			return null;
		}
		
		String retJson = null;
		
		HttpPost post = new HttpPost(url);
		addHeader(post);
		
		
		StringEntity entity = new StringEntity(jsonMsg, ContentType.APPLICATION_JSON);
		post.setEntity(entity);
		if(logger.isDebugEnabled()){
			logger.debug("http request url = " + url);
			logger.debug("http request json body = " + EntityUtils.toString(entity));
		}
		try(CloseableHttpResponse response = getHttpClient().execute(post)){
			HttpEntity respEntity = response.getEntity();
			if(null != respEntity){
				retJson = EntityUtils.toString(respEntity, defaultCharset);
			}
		}
		logger.debug("httpclient post, response = " + retJson);
		return retJson;
	}
	
	/**
	 * 
	 * @param url
	 * @param params 参数：如 param1=1&param2=2
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPostByParam(String url, String params) throws ClientProtocolException, IOException{
		if(TextUtils.isEmpty(url)){
			return null;
		}
		
		String retStr = null;
		
		HttpPost post = new HttpPost(url);
		
		StringEntity entity = new StringEntity(params, ContentType.APPLICATION_FORM_URLENCODED);

		post.setEntity(entity);
		
		if(logger.isDebugEnabled()){
			logger.debug("http request url = " + url);
			logger.debug("http request json body = " + EntityUtils.toString(entity));
		}
		try(CloseableHttpResponse response = getHttpClient().execute(post)){
			HttpEntity respEntity = response.getEntity();
			if(null != respEntity){
				retStr = EntityUtils.toString(respEntity, defaultCharset);
			}
		}
		logger.debug("httpclient post, response = " + retStr);
		return retStr;
	}
	
	private static void addHeader(HttpRequestBase request){
		//解决４０６错误
		request.addHeader(HttpHeaders.ACCEPT, "application/json");
		request.addHeader(HttpHeaders.ACCEPT_CHARSET, defaultCharset);
		request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
	}
	
	public static String generateQueryStr(Map<String, String> paramMap) {
		StringBuilder queryStr = new StringBuilder();
		int i = 0;
		for (String key : paramMap.keySet()) {
			i++;
			if (paramMap.get(key) == null || "".equals(paramMap.get(key))) {
				continue;
			}
			if (i == 1) {
				queryStr.append("?");
			} else {
				queryStr.append("&");
			}
			queryStr.append(key);
			queryStr.append("=");
			queryStr.append(paramMap.get(key));
		}
		logger.debug("queryStr={}", queryStr);
		return queryStr.toString();
		
	}
	
	public static void main(String[] args) {
		try {
			String result = doPost("http://localhost:8080/omega-web/custInfo/ajaxListPersonal", "");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
