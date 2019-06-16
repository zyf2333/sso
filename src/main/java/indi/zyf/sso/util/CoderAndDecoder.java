package indi.zyf.sso.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author LCJA
 *
 */

public class CoderAndDecoder {
	//初始向量  
    public static final String VIPARA = "aabbccddeeffgghh";  
	
	private static Logger logger = LoggerFactory.getLogger(CoderAndDecoder.class);
	
	//编码方式  
    public static final String BM = "UTF-8";  
	
	 private static final String ASE_KEY="aabbccddeeffgghh";
	
	 /** 
	     * 加密 
	     *  
	     * @param cleartext 
	     * @return 
	     */  
	    public static String encrypt(String cleartext) {  
	        //加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：aabbccddeeffgghh  
	        try {  
	            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
	            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES  
	            SecretKeySpec key = new SecretKeySpec(ASE_KEY.getBytes(), "AES");  
	            //实例化加密类，参数为加密方式，要写全  
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密  
	             //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec  
	            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
	            //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式  
	            byte[] encryptedData = cipher.doFinal(cleartext.getBytes(BM));  
	  
	            return new BASE64Encoder().encode(encryptedData);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return "";   
	        }  
	    }  
	  
	    /** 
	     * 解密 
	     *  
	     * @param encrypted 
	     * @return 
	     */  
	    public static String decrypt(String encrypted) {  
	        try {  
	            byte[] byteMi = new BASE64Decoder().decodeBuffer(encrypted);  
	            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
	            SecretKeySpec key = new SecretKeySpec(  
	                    ASE_KEY.getBytes(), "AES");  
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	            //与加密时不同MODE:Cipher.DECRYPT_MODE  
	            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
	            byte[] decryptedData = cipher.doFinal(byteMi);  
	            return new String(decryptedData, BM);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return "";  
	        }  
	    }  
    
    public static String getMD5(String str){
    	String slat="haha";
		String base=str+"/"+slat;
		String md5= DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
    
    
    
    
    public static void main(String[] args) throws UnsupportedEncodingException {
		/*String str ="1qaz2wsx";
		byte[] bytearr = encrypt(str,"fp");
		System.out.println(new String(bytearr,"utf8"));
		byte[] bytearr2 = decrypt(bytearr,"fp");
		System.out.println(new String(bytearr2,"utf8"));*/
    	String rtstr = encrypt("19960719asd");
    	System.out.println(rtstr);
    	System.out.println(decrypt(rtstr));
    	/*byte [] byarr = DeMd5(rtstr.getBytes());
    	System.out.println(new String(byarr,"gbk"));*/
    	
    	
    	
		}
}

