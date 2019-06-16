package indi.zyf.sso.util;

import indi.zyf.sso.core.SystemConstants;
import indi.zyf.sso.vo.wx.AccessToken;
import indi.zyf.sso.vo.wx.Jsapi;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WeixinUtil {

	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	public static String Get_OpenId = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static InputStream sendGetHaveInput() {
		return null;
	}

	public static String getOpenid(String code) {
		return httpRequest(Get_OpenId.replace("APPID", SystemConstants.APPID_SCHOOL)
				.replace("SECRET", SystemConstants.APPSECRET_SCHOOL).replace("JSCODE", code), "GET", null);
	}

	public static String getOpenidUseType(String code,String type) {
		if("parent".equals(type)){
			return httpRequest(Get_OpenId.replace("APPID", SystemConstants.APPID_PARENT)
					.replace("SECRET", SystemConstants.appsecret_PARENT).replace("JSCODE", code), "GET", null);
		}else if("school".equals(type)){
			return httpRequest(Get_OpenId.replace("APPID", SystemConstants.APPID_SCHOOL)
					.replace("SECRET", SystemConstants.APPSECRET_SCHOOL).replace("JSCODE", code), "GET", null);
		}else{
			return null;
		}

	}

	public static AccessToken getAccessToken(String appid, String appsecret) {
		String requestUrl = SystemConstants.GET_ACCESSTOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		logger.debug("获得access_token地址: " + requestUrl);
		String json = httpRequest(requestUrl, "GET", null);
		AccessToken accessToken = JsonUtil.fromJson(json, AccessToken.class);
		// 如果请求成功
		if (accessToken != null) {
			logger.debug("accesstoken 获取成功，ACCESSTOKEN：" + accessToken.getAccessToken() + " EXPIRES:"
					+ accessToken.getExpiresIn());
		} else {
			logger.debug("accesstoken 获取失败!");
		}
		return accessToken;
	}

	public static Jsapi getJsapi(String nonce) throws IOException {
		Map<String, String> ret = new HashMap<String, String>();
		String nonceStr = nonce;// "dphQrj4oAJNHhpdW";
		String timestamp = String.valueOf(System.currentTimeMillis());
		String signature = "";
		String rspJson;
		rspJson = sendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket",
				"access_token=" + SystemConstants.ACCESS_TOKEN + "&type=jsapi");

		logger.debug("jsapi:" + rspJson);
		Jsapi jsapi = JsonUtil.fromJson(rspJson, Jsapi.class);
		return jsapi;
	}

	/**
	 * 发get请求
	 *
	 * @param url 地址
	 * @param param  a=a&b=b&c=c
	 *
	 */
	public static String sendGet(String url, String param) throws IOException {
		logger.debug("发送get请求url:{} param:{}", url, param);
		String result = "";
		BufferedReader in = null;
		String urlNameString = "";
		if (param == null || param == "") {
			urlNameString = url;
		} else {
			urlNameString = url + "?" + param;
		}
		logger.debug("url:" + urlNameString);
		URL realUrl = new URL(urlNameString);
		logger.debug(realUrl.toString());
		// 打开和URL之间的连接
		URLConnection connection = realUrl.openConnection();
		// 设置通用的请求属性
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		// 建立实际的连接
		connection.connect();
		// 获取所有响应头字段
		Map<String, List<String>> map = connection.getHeaderFields();
		// 遍历所有的响应头字段
		logger.debug("遍历响应头");
		for (String key : map.keySet()) {
			logger.debug(key + "--->" + map.get(key));
		}
		// 定义 BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		return result;
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		String jsonstr = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			logger.debug("设置请求方式");
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			logger.debug("开始接收返回数据");
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			jsonstr = buffer.toString();

		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.", ce);
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		logger.info("jsonstr:{}", jsonstr);
		return jsonstr;
	}

	public static Map<String, String> httpRequestUseInput(String requestUrl, String requestMethod, String outputStr) {
		Map<String, String> param = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			logger.debug("设置请求方式");
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			logger.debug("开始接收返回数据");
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			param = XmlUtil.parseXml(inputStream);
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.", ce);
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		return param;
	}

	/**
	 * 通过okhttp发送get 请求
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String sendGetUseOkHttp(String url, Map<String, String> head) throws IOException {
		String rtstr = "";
		logger.debug("通过OKHTTP发送get请求");
		OkHttpClient client = new OkHttpClient();
		// 3, 发起新的请求, 得到响应对象
		Request.Builder builder = new Request.Builder().url(url);
		if (head != null) {
			Iterator it = head.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				logger.debug("key:{},value:{}", key, head.get(key));
				builder.addHeader(key, head.get(key));
			}
		}
		Request request = builder.build();

		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			rtstr = response.body().string();
		}
		logger.debug("okhttp返回结果:{}", rtstr);
		return rtstr;
	}

	/**
	 * 通过okhttp发送post 请求
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String sendPostUseOkHttp(String url, String json) throws IOException {
		String rtstr = "";
		OkHttpClient client = new OkHttpClient();

		// , 发起新的请求,获取返回信息
		FormBody.Builder builder = new FormBody.Builder();
		/*
		 * .add("useName", "abc")//添加键值对 .add("usePwd", "321") .build();
		 */

		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		logger.debug("message---" + response.message());
		logger.debug("code---" + response.code());
		if (response.isSuccessful()) {
			rtstr = response.body().string();
		}
		return rtstr;
	}

	public static Date getNextDate() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取推送时间
	 *
	 * @return
	 */
	public static int getTSTime() {
		Date timenow = new Date();
		int hours = timenow.getHours();
		Date datemeal = null;
		// 如果小于19点就19点更新 ，如果大于19点就次日更新
		if (hours < 19) {
			datemeal = timenow;
		} else {
			datemeal = WeixinUtil.getNextDate();
		}
		String tempDate = DateUtil.getStringByPattern(datemeal, "yyyyMMdd");
		int gettime = Integer.valueOf(tempDate);
		return gettime;
	}

	public static String sign(String param) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String signature = "";
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(param.getBytes("UTF-8"));
		signature = byteToHex(crypt.digest());
		return signature;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static void main(String[] args) {
	}

}
