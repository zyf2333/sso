package indi.zyf.sso.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具
 *
 */
public class StringUtil {
	public static final String EMPTY_STRING = "";
	public static final int INDEX_NOT_FOUND = -1;
	public static final char SPACE = ' ';
	public static final String SPACE_STRING = " ";
	public static final char COMMA = ',';
	public static final String COMMA_STRING = ",";
	public static final char COLON = ':';
	public static final String COLON_STRING = ":";
	public static final char SEMI_COLON = ';';
	public static final String SEMI_COLON_STRING = ";";
	public static final char DOT = '.';
	public static final String DOT_STRING = ".";
	public static final char AT_SIGN = '@';
	public static final String AT_SIGN_STRING = "@";
	public static final char AMPERSAND = '&';
	public static final String AMPERSAND_STRING = "&";
	public static final char HYPHEN_MINUS = '-';
	public static final String HYPHEN_MINUS_STRING = "-";
	public static final char SLASH = '/';
	public static final String SLASH_STRING = "/";
	public static final char BACKSLASH = '\\';
	public static final String BACKSLASH_STRING = "\\";
	public static final char APOSTROPHE = '\'';
	public static final String APOSTROPHE_STRING = "'";
	public static final char NUMBER_SIGN = '#';
	public static final String NUMBER_SIGN_STRING = "#";
	public static final char PIPE = '|';
	public static final String PIPE_STRING = "|";
	public static final char UNDERSCORE = '_';
	public static final String UNDERSCORE_STRING = "_";
	public static final char ASTERISK = '*';
	public static final String ASTERISK_STRING = "*";
	public static final char GREATER_THAN_SIGN = '>';
	public static final String GREATER_THAN_SIGN_STRING = ">";
	public static final char LESSER_THAN_SIGN = '<';
	public static final String LESSER_THAN_SIGN_STRING = "<";
	public static final char PERCENTAGE_SIGN = '%';
	public static final char PERCENTAGE_SIGN_STRING = '%';
	public static final char CARET = '^';
	public static final String CARET_STRING = "^";
	public static final Pattern HTML_TAG_PATTERN = Pattern.compile("(i?)<[^>]+>");
	public static final Pattern	EMAIL_PATTERN = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	public static final Pattern MOBILE_PATTERN = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
	// 用于匹配富文本中图片标签的正则表达式：[img][/img]
	public static final String REGEX_IMG = "(\\[img\\].*?\\[/img\\])";

	private StringUtil() {
	}

	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "").toLowerCase();
	}

	/**
	 * 字符串拼接
	 * 
	 * @param strings
	 * @return
	 */
	public static String concatenate(final String... strings) {
		if (strings == null) {
			return null;
		}

		if (strings.length == 0) {
			return EMPTY_STRING;
		}

		final StringBuilder sb = new StringBuilder();

		for (final String string : strings) {
			if (!isEmpty(string)) {
				sb.append(string);
			}
		}

		return sb.toString();
	}

	/**
	 * 检测是否包含指定字符
	 * 
	 * @param cs
	 * @param c
	 * @return boolean
	 */
	public static boolean isContains(final CharSequence cs, final char c) {
		if (isEmpty(cs)) {
			return false;
		}

		final int length = cs.length();
		for (int i = 0; i < length; ++i) {
			if (cs.charAt(i) == c) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断是否为空或 null
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isEmpty(final CharSequence cs) {
		return (cs == null) || (cs.length() == 0);
	}

	/**
	 * 匹配指定字符串并以指定字符串替换
	 * 
	 * @param s
	 * @param target
	 * @param replacement
	 * @return
	 */
	public static String replace(final String s, final String target, final String replacement) {

		if (isEmpty(s)) {
			return s;
		}

		if (isEmpty(target)) {
			return s;
		}

		if (replacement == null) {
			return s;
		}

		// holds the result
		final StringBuilder sb = new StringBuilder();

		// position in original
		int current = 0;

		// start of the string to be replaced
		int index = s.indexOf(target);

		final int targetLength = target.length();

		while (index >= 0) {
			sb.append(s.substring(current, index));
			sb.append(replacement);

			current = index + targetLength;
			index = s.indexOf(target, current);
		}

		// remember to append any characters to the right of a match
		sb.append(s.substring(current));

		return sb.toString();
	}

	/**
	 * 正则匹配查找字符串并以指定字符串替换
	 * 
	 * @param s
	 * @param target
	 * @param replacement
	 * @return
	 */
	public static String replace(final String s, final Pattern target, final String replacement) {

		if (isEmpty(s)) {
			return EMPTY_STRING;
		}

		if (target == null) {
			return s;
		}

		return target.matcher(s).replaceAll(replacement);
	}

	public static String replace(final String s, Integer startPos, Integer endPos, final String replacement) {
		if (isEmpty(s)) {
			return EMPTY_STRING;
		}

		if (isEmail(replacement)) {
			return s;
		}

		if ((startPos > s.length()) || (endPos > s.length()) || (startPos >= endPos)) {
			return s;
		}

		return s.substring(0, startPos) + replacement + s.substring(endPos, s.length());

	}

	/**
	 * 以指定分隔符分割字符串，结果集为String数组
	 * 
	 * @param cs
	 * @param separator
	 * @return
	 */
	public static String[] split(final CharSequence cs, String separator) {
		if (cs == null) {
			return null;
		}

		if (cs.length() == 0) {
			return new String[1];
		}

		if (separator == null) {
			separator = "\\s";
		}

		final ArrayList<String> matchList = new ArrayList<String>();

		final Matcher m = Pattern.compile(separator).matcher(cs);

		int index = 0;
		String match;
		while (m.find()) {
			match = cs.subSequence(index, m.start()).toString();

			if (isEmpty(match)) {
				matchList.add(EMPTY_STRING);
			} else {
				matchList.add(match.trim());
			}

			index = m.end();
		}

		if (index == 0) {
			return new String[] { cs.toString() };
		}

		match = cs.subSequence(index, cs.length()).toString();
		if (isEmpty(match)) {
			matchList.add(EMPTY_STRING);
		} else {
			matchList.add(match.trim());
		}

		// Construct result
		final int resultSize = matchList.size();
		final String[] result = new String[resultSize];

		return matchList.subList(0, resultSize).toArray(result);
	}

	/**
	 * 以指定分隔符分割字符串，结果集为String数组
	 * 
	 * @param s
	 * @param separator
	 * @return
	 */
	public static String[] split(final String s, final char separator) {
		final List<String> result = splitToList(s, separator);

		return result.toArray(new String[result.size()]);
	}

	/**
	 * 以指定分割符分割字符串，结果集为List
	 * 
	 * @param s
	 * @param separator
	 * @return
	 */
	public static List<String> splitToList(final String s, char separator) {
		final List<String> result = new ArrayList<String>();

		if (isEmpty(s)) {
			return result;
		}

		String token;

		int start = 0;
		int end = s.indexOf(separator);
		while (end >= 0) {
			token = s.substring(start, end);
			result.add(token);
			start = end + 1;
			end = s.indexOf(separator, start);
		}

		token = s.substring(start);
		result.add(token);

		return result;
	}

	/**
	 * 转换list为string，
	 * 
	 * @param list
	 * @return
	 */
	public static String toString(final List<?> list) {

		return toString(list, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
	}

	/**
	 * 以指定分隔符转换list为string
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String toString(final List<?> list, final String separator) {

		return toString(list, EMPTY_STRING, EMPTY_STRING, separator);
	}

	/**
	 * 以指定分隔符转换list为string，且添加指定头尾字符串
	 * 
	 * @param list
	 * @param header
	 * @param footer
	 * @param separator
	 * @return
	 */
	public static String toString(final List<?> list, final String header, final String footer,
			final String separator) {

		if (list == null) {
			return EMPTY_STRING;
		}

		final int size = list.size();
		if (size == 0) {
			return EMPTY_STRING;
		}

		final StringBuilder buf = new StringBuilder(size);

		if (!isEmpty(header)) {
			buf.append(header);
		}

		for (int i = 0; i < size; ++i) {
			if (i != 0) {
				if (separator != null) {
					buf.append(separator);
				}
			}

			buf.append(list.get(i));
		}

		if (!isEmpty(footer)) {
			buf.append(footer);
		}

		return buf.toString();
	}

	public static String trim(final String s) {
		return (s == null) ? null : s.trim();
	}

	public static boolean isEmail(String email) {
		if (isEmpty(email)) {
			return false;
		}
		Matcher m = EMAIL_PATTERN.matcher(email);
		return m.matches();
	}

	/**
	 * 
	 * 
	 * China Mobile:
	 * 134、135、136、137、138、139、150、151、157(TD)、158、159、182、183、187、188、183、140、
	 * 141、142、143、144、146、147、148、149
	 * 
	 * China Unicom: 130、131、132、152、155、156、185、186、145
	 * 
	 * China Telecom: 133、153、180、181、189、（1349卫通）
	 * 
	 * 虚拟运营商: 170 ~ 179
	 */
	public static boolean isMobile(String mobile) {
		if (isEmpty(mobile)) {
			return false;
		}

		Matcher m = MOBILE_PATTERN.matcher(mobile);
		return m.matches();
	}

	private static byte[] getBytes(final String content, final Charset charset) {
		if (content == null) {
			return null;
		}
		return content.getBytes(charset);
	}

	private static String newString(final byte[] bytes, final Charset charset) {
		return bytes == null ? null : new String(bytes, charset);
	}

	public static byte[] getBytesUtf8(final String content) {
		return getBytes(content, Charset.forName("UTF-8"));
	}

	public static String newStringUtf8(final byte[] bytes) {
		return newString(bytes, Charset.forName("UTF-8"));
	}

	public static int getStrLength(String s) {
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		return (int) ((s.length() + 1) / 2);
	}

	/**
	 * 计算除去图片信息以外的汉字长度
	 * 
	 * @param s
	 * @return
	 */
	public static int getStrLengthWithoutImg(String s) {
		Pattern pattern = Pattern.compile(REGEX_IMG);
		Matcher m = pattern.matcher(s);
		String output = m.replaceAll("");
		output = output.replaceAll("[^\\x00-\\xff]", "**");
		return (int) ((output.length() + 1) / 2);
	}

	/**
	 * 计算文本中的图片数量
	 * 
	 * @param s
	 * @return
	 */
	public static int getImgCount(String s) {
		int i = 0;
		Pattern pattern = Pattern.compile(REGEX_IMG);
		Matcher m = pattern.matcher(s);
		while (m.find()) {
			i++;
		}
		return i;
	}
	
	/** 
	 * 计算采用某种编码方式时字符串所占字节数 
	 * 汉字字节数    ISO8859-1 占2 ,GB2312 & GBK 占4 ,utf-8 占6
	 * @param content 
	 * @return 
	 */  
	public static int getByteSize(String content,String format) {  
	    int size = 0;  
	    if (null != content) {  
	        try {  
	            size = content.getBytes(format).length;  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return size;  
	}  
	/**
	 * 输入字符串修改为null或首位加%（用于搜索）
	 * @param src
	 * @return
	 */
	public static String addPercent(String src){
		if(StringUtil.isEmpty(src)||src.equals("{}")){
			src=null;
		}else{
			src="%"+src+"%";
		}
		return src;
	}
	
	public static String randomStr(int n){
		String temp ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";//62
		String rt = "";
		for(int i=0;i<n;i++){
			rt+=temp.charAt((int)(Math.random()*61));
		}
		return rt;
	}
	
	public static void main(String[] args) {
		System.out.println(isEmail("123456@163.com"));
		System.out.println(isEmail("123456@163"));
		System.out.println(isEmail("123456123123"));
		System.out.println(isMobile("15034137740"));
	}
}
