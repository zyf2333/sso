package indi.zyf.sso.core;


import java.util.HashMap;
import java.util.Map;

public class SystemConstants {
	
	//public static  Queue<Report> reportQueue = new ConcurrentLinkedQueue<Report>();
	
	public static String [] ch ={"a","b","c","d","e","f","g","h","i","j",
			"k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J",
			"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};

	public static String APPID_SCHOOL = "";
	public static String APPSECRET_SCHOOL = "";

	public static String APPID_PARENT = "";
	public static String appsecret_PARENT = "";

	public static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static String ACCESS_TOKEN = "";
	public static String TICKET = "";

	
	public static Map<Integer,String> field = new HashMap<Integer,String>();
	
	
	public static Map<Integer,String> field2 = new HashMap<Integer,String>();
	
	public static Map<Integer,String> pro = new HashMap<Integer,String>(38);
	
	public static Map<Integer,String> level = new HashMap<Integer,String>();
	
	public static final Integer CACHE_EXPIRE_TIME = 60 * 60 *3;
	
	public static final String ROLE_MANAGER = "ROLE_MANAGER";


	/**
	 *
	 *	==================验证码相关配置=========================
	 */
	/**
	 * 默认的用户名密码登录请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "";///authentication/form
	/**
	 * 默认的手机验证码登录请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_SMS = "/sysuser/registBySms";
	/**
	 * 默认的邮箱验证码登录请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_EMAIL = "/sysuser/registByEmail";

	/**
	 * 验证图片验证码时，前端传图片验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	/**
	 * 需要验证码拦截器拦截过滤图片验证码的请求列表/code/sms
	 */
	public static final String PARAMETER_CODE_IMAGE_URLS = "";
	/**
	 * 发送短信验证码 或 验证短信验证码时，前端传验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
	/**
	 * 需要验证码拦截器拦截过滤手机验证码的请求列表
	 */
	public static final String PARAMETER_CODE_SMS_URLS = "";
	/**
	 * 发送邮箱验证码 或 验证邮箱验证码时，前端传验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_EMAIL = "emailCode";
	/**
	 * 邮件发送方的地址
	 */
	public static final String DEFAULT_EMAIL_FROM = "zyf69172@163.com";

	public static final String DEFAULT_EMAIL_CODE_CONTENT = "你的验证码为【CODE】，请妥善保管";
	/**
	 * 需要验证码拦截器拦截过滤图片验证码的请求列表
	 */
	public static final String PARAMETER_CODE_EMAIL_URLS = "";
	/**
	 * 获取验证码的url前缀
	 */
	public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
	/**
	 * 默认项目名
	 */
	public static final String DEFAULT_PATH = "/sso";
	/**
	 * debug模式验证码
	 */
	public static final String DEFAULT_DEBUG_CODE = "666666";





	
}