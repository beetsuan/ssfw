/**
 * Copyright by MoGenesis. Created by myc
 */
package com.ssfw.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author a
 */
public class StringUtil {

	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 前导标识
	 */
	public static final int BEFORE = 1;

	/**
	 * 后继标识
	 */
	private static final int AFTER = 2;

	/**
	 * 默认的分隔符
	 */
	private static final String DEFAULT_PATH_SEPARATOR = ",";

	private static final String UNDERLINE="_";
	
	public static boolean isNull(Object stringObj) {

		if (null == stringObj){
			return true;
		}
		if (StringUtils.isBlank(stringObj.toString())) {
			return true;
		}
        return "null".equals(stringObj);
    }
	
	public static boolean isNotNull(Object stringObj) {
		
		return !isNull(stringObj);
	}

	
	public static String setEmptyToNull(Object stringObj) {

		if(null == stringObj) {
			return null;
		}

		if (isNotNull(stringObj.toString())) {
			return stringObj.toString();
		}
		return null;
	}
	
	/**
	 * 生成随机字符串
	 * 
	 */
	public static String generateMobileRandomStr(int randStrLength) {
		// 写入你所希望的所有的字母A-Z,a-z,0-9
		String randStr = "0123456789";
		StringBuilder generateRandStr = new StringBuilder();
		Random rand = new Random();
		// int randStrLength = 4; // 你想生成的随机数的长度
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(10);
			generateRandStr.append(randStr.charAt(randNum));
		}
		return generateRandStr.toString();
	}

	/**
	 * 生成随机字符串---不得有重复值
	 * 
	 */
	public static int[] getRandomStrByLength(int length, int randSize) {
		int[] randStr = new int[randSize];
		Random rand = new Random();
		for (int i = 0; i < randStr.length; i++) {
			int randNum = rand.nextInt(length);
			if (i == 0) {
				randStr[i] = randNum;
			} else {
				boolean b = true;
				for (int k : randStr) {
					if (k == randNum) {
						i--;
						b = false;
						break;
					}
				}
				if (b) {
					randStr[i] = randNum;
				}
			}
		}
		return randStr;
	}

	/**
	 * 生成随机字符串
	 * 
	 */
	public static String generateRandomStr() {
		// 写入你所希望的所有的字母A-Z,a-z,0-9
		String randStr = "ABCDEFGHIabcdef0123456789";
		StringBuilder generateRandStr = new StringBuilder();
		Random rand = new Random();
		// 你想生成的随机数的长度
		int randStrLength = 5;
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(25);
			generateRandStr.append(randStr.charAt(randNum));
		}
		return "_" + generateRandStr.toString();
	}

	/**
	 * 判断传入的字符串是否是一个合法的移动手机号码
	 * 
	 * @param str 字符串
	 * @return true or false
	 */
	public static boolean isMobileNum(String str) {
		if (str != null) {
			return str.matches("(13|14|15|16|17|18|19)[0-9]{9}");
		} else {
			return false;
		}
	}

	/**
	 * 检查邮箱有效性
	 * 
	 * @param str 字符串
	 * @return
	 */
	public static boolean isEmail(String str) {
        return str.matches("[\\w\\W]{3,30}@(?:\\w+\\.)+\\w+");
	}

	/**
	 * 返回字符串的字节长度
	 * 
	 * @param str 字符串
	 * @return
	 */
	public static int getStrBytesLength(String str) {

		if ((str != null) && (str.length() > 0)) {
			return str.getBytes().length;
		}
		return 0;
	}

	/**
	 * 返回字符串的存储占用长度（中文占两个字节，英文占一个）
	 * @param str 字符串
	 * @return
	 */
	public static int getStrLength(String str) {

		if (null == str || "".equals(str)){
			return 0;
		}

		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}


	/**
	 * 过滤字符串里的特殊字符
	 * 
	 * @param msg1
	 * @return
	 */
	public static String strFilter(String msg1) {

		boolean b = true;
		StringBuilder msg = new StringBuilder(msg1);
		if (!"".contentEquals(msg)) {
			for (int i = 0; i < msg.length(); i++) {
				if (msg.charAt(i) == '\\' && i != msg.length() - 1) {
					if (msg.charAt(i + 1) != 'n') {
						msg.insert(i, '\\');
						i++;
					}
					b = false;
				}
				if (msg.charAt(i) == '\"' && b) {
					msg.insert(i, '\\');
					i++;
				}
				b = true;
			}
		}
		return msg.toString();
	}

	/**
	 * 输出字符串长度
	 * 
	 * @param str 字符串
	 * @param len 返回字符串的长度
	 * @return
	 */
	public static String outStr(String str, Integer len) {
		if (getStrBytesLength(str) > len) {
			for (int i = 0; i < (len); i++) {

				if (getStrBytesLength(str.substring(0, i + 1)) > len) {
					return str.substring(0, i);
				}
			}
			return str.substring(0, len);
		}
		return strFilter(str);
	}

	/**
	 * 过滤非法字符
	 * 
	 * @param input
	 * @return
	 */
	public static String filter(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuilder filtered = new StringBuilder(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&#034;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			case '\'':
				filtered.append("&#039;");
				break;
			default:
				filtered.append(c);
			}

		}
		return (filtered.toString());
	}

	/**
	 * 判断是否存在特殊字符
	 * 特殊字符：>&"<
	 * 
	 * @param input
	 * @return
	 */
	public static boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
					case '>':
					case '&':
					case '"':
					case '<':
						flag = true;
					break;
					default:
				}
			}
		}
		return flag;
	}

	/**
	 * 输出字符串的长度
	 * 
	 * @param str 字符串
	 * @param len 输出长度
	 * @return
	 */
	public static String outStrByLen(String str, Integer len) {
		String s = splitStrByLen(str, len);
		s = filter(s);
		return s;
	}

	/**
	 * 按字符串的长度输出字符串,超出的内容显示为...
	 * 
	 * @param str 字符串
	 * @param len 输出长度
	 * @return
	 */
	public static String splitStrByLen(String str, Integer len) {
		if (getStrBytesLength(str) > len) {
			for (int i = 0; i < (len - 3); i++) {
				if (getStrBytesLength(str.substring(0, i + 1)) > (len - 3)) {
					return str.substring(0, i) + "...";
				}
			}
			return str.substring(0, len - 3) + "...";
		}
		return strFilter(str);

	}

	/**
	 * 判断字符串是否为中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean getChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) > 128) {
				return true;
			}

		}
		return false;

	}

	/**
	 * 判断字符串转型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean stringToLong(String str) {
		boolean b = false;
		try {
			Long.parseLong(str);
			b = true;
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	public static byte[] gbk2utf8(String chenese) {
		char[] c = chenese.toCharArray();
		byte[] fullByte = new byte[3 * c.length];
		for (int i = 0; i < c.length; i++) {
			int m = c[i];
			String word = Integer.toBinaryString(m);

			StringBuilder sb = new StringBuilder();
			int len = 16 - word.length();
			// 补零
			for (int j = 0; j < len; j++) {
				sb.append("0");
			}
			sb.append(word);
			sb.insert(0, "1110");
			sb.insert(8, "10");
			sb.insert(16, "10");

			String s1 = sb.substring(0, 8);
			String s2 = sb.substring(8, 16);
			String s3 = sb.substring(16);

			byte b0 = Integer.valueOf(s1, 2).byteValue();
			byte b1 = Integer.valueOf(s2, 2).byteValue();
			byte b2 = Integer.valueOf(s3, 2).byteValue();
			byte[] bf = new byte[3];
			bf[0] = b0;
			fullByte[i * 3] = bf[0];
			bf[1] = b1;
			fullByte[i * 3 + 1] = bf[1];
			bf[2] = b2;
			fullByte[i * 3 + 2] = bf[2];

		}
		return fullByte;
	}
	/**
	 * 将一个中间带逗号分隔符的字符串转换为ArrayList对象
	 * 
	 * @param str
	 *            待转换的符串对象
	 * @return ArrayList对象
	 */
	public static List<String> toList(String str) {
		return toList(str, DEFAULT_PATH_SEPARATOR);
	}

	/**
	 * 将字符串对象按给定的分隔符separator转象为ArrayList对象
	 * 
	 * @param str
	 *            待转换的字符串对象
	 * @param separator
	 *            字符型分隔符
	 * @return ArrayList对象
	 */
	public static List<String> toList(String str, String separator) {
		StringTokenizer strTokens = new StringTokenizer(str, separator);
		List<String> list = new ArrayList<>();

		while (strTokens.hasMoreTokens()) {
			list.add(strTokens.nextToken().trim());
		}
		return list;
	}

	/**
	 * 将一个中间带逗号分隔符的字符串转换为字符型数组对象
	 * 
	 * @param str
	 *            待转换的符串对象
	 * @return 字符型数组
	 */
	public static String[] toArray(String str) {
		return toArray(str, DEFAULT_PATH_SEPARATOR);
	}

	/**
	 * 将字符串对象按给定的分隔符separator转象为字符型数组对象
	 * 
	 * @param str
	 *            待转换的符串对象
	 * @param separator
	 *            字符型分隔符
	 * @return 字符型数组
	 */
	public static String[] toArray(String str, String separator) {
		StringTokenizer strTokens = new StringTokenizer(str, separator);
		String[] strArray = new String[strTokens.countTokens()];
		int i = 0;

		while (strTokens.hasMoreTokens()) {
			strArray[i] = strTokens.nextToken().trim();
			i++;
		}

		return strArray;
	}

	/**
	 * 转换给定字符串的编码格式
	 * 
	 * @param inputString
	 *            待转换字符串对象
	 * @param inencoding
	 *            待转换字符串的编码格式
	 * @param outencoding
	 *            准备转换为的编码格式
	 * @return 指定编码与字符串的字符串对象
	 */
	public static String encodingTransfer(String inputString,
			String inencoding, String outencoding) {
		if (inputString == null || inputString.length() == 0) {
			return inputString;
		}
		try {
			return new String(inputString.getBytes(outencoding), inencoding);
		} catch (Exception e) {
			return inputString;
		}
	}

	/**
	 * 删除字符串中指定的字符 只要在delStrs参数中出现的字符，并且在inputString中也出现都会将其它删除
	 * 
	 * @param inputString
	 *            待做删除处理的字符串
	 * @param delStrs
	 *            作为删除字符的参照数据，用逗号分隔。如果要删除逗号可以在这个字符串再加一个逗号
	 * @return 返回一个以inputString为基础不在delStrs出现新字符串
	 */
	public static String delString(String inputString, String delStrs) {
		if (inputString == null || inputString.length() == 0) {
			return inputString;
		}

		String[] strs = toArray(delStrs);
		for (String str : strs) {
			if ("".equals(str)) {
				inputString = inputString.replaceAll(" ", "");
			} else {
				if (str.compareTo("a") >= 0) {
					inputString = replace(inputString, str, "");
				} else {
					inputString = inputString.replaceAll(str, "");
				}
			}
		}
		if (subCount(delStrs, ",") > strs.length) {
			inputString = inputString.replaceAll("\\,", "");
		}

		return inputString;
	}

	/**
	 * 去除左边多余的空格。
	 * 
	 * @param value
	 *            待去左边空格的字符串
	 * @return 去掉左边空格后的字符串
	 */
	public static String trimLeft(String value) {
		String result = value;
		if (result == null) {
			return result;
		}
		char[] ch = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 去除右边多余的空格。
	 * 
	 * @param value
	 *            待去右边空格的字符串
	 * @return 去掉右边空格后的字符串
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null) {
			return result;
		}
		char[] ch = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * 判断一个字符串中是否包含另一个字符串
	 * 
	 * @param parentStr
	 *            父串
	 * @param subStr
	 *            子串
	 * @return 如果父串包含子串的内容返回真，否则返回假
	 */
	public static boolean isInclude(String parentStr, String subStr) {
		boolean hasSub = false;
		for (int i = 0; i < (parentStr.length() - subStr.length() + 1); i++) {
			String tempString = parentStr.substring(i, i + subStr.length());
			if (subStr.equals(tempString)) {
				hasSub = true;
				break;
			}
		}

		return hasSub;
	}

	/**
	 * 判断一个字符串中是否包含另一个字符串数组的任何一个
	 * 
	 * @param parentStr
	 *            父串
	 * @param subStrs
	 *            子串集合
	 * @return 如果父串包含子串的内容返回真，否则返回假
	 */
	public static boolean isIncludes(String parentStr, String subStrs) {
		String[] subStrArray = toArray(subStrs);
		for (String subStr : subStrArray) {
			if (isInclude(parentStr, subStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个子字符串在父串中重复出现的次数
	 * 
	 * @param parentStr
	 *            父串
	 * @param subStr
	 *            子串
	 * @return 子字符串在父字符串中重得出现的次数
	 */
	public static int subCount(String parentStr, String subStr) {
		int count = 0;

		for (int i = 0; i < (parentStr.length() - subStr.length()); i++) {
			String tempString = parentStr.substring(i, i + subStr.length());
			if (subStr.equals(tempString)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * 得到在字符串中从开始字符串到结止字符串中间的子串
	 * 
	 * @param parentStr
	 *            父串
	 * @param startStr
	 *            开始串
	 * @param endStr
	 *            结止串
	 * @return 返回开始串与结止串之间的子串
	 */
	public static String subString(String parentStr, String startStr,
			String endStr) {
		return parentStr.substring(parentStr.indexOf(startStr)
				+ startStr.length(), parentStr.indexOf(endStr));
	}

	/**
	 * 得到在字符串中从开始字符串到结止字符串中间的子串的集合
	 * 
	 * @param parentStr
	 *            父串
	 * @param startStr
	 *            开始串
	 * @param endStr
	 *            结止串
	 * @return 返回开始串与结止串之间的子串的集合
	 */
	public static List<String> subStringList(String parentStr, String startStr,
			String endStr) {
		List<String> result = new ArrayList<>();
		List<String> elements = splitToList(parentStr, startStr, endStr);
		for (String element : elements) {
			if (!isIncludes(element, startStr) || !isIncludes(element, endStr)) {
				continue;
			}
			result.add(subString(element, startStr, endStr));
		}
		return result;

	}

	/**
	 * 将指定的String转换为Unicode的等价值
	 * 
	 * 基础知识： 所有的转义字符都是由 '\' 打头的第二个字符 0-9 :八进制 u :是Unicode转意，长度固定为6位
	 * Other:则为以下字母中的一个 b,t,n,f,r,",\ 都不满足，则产生一个编译错误 提供八进制也是为了和C语言兼容. b,t,n,f,r
	 * 则是为控制字符.书上的意思为:描述数据流的发送者希望那些信息如何被格式化或者被表示. 它可以写在代码的任意位置，只要转义后是合法的. 例如:
	 * int c=0\u003b 上面的代码可以编译通过，等同于int c=0; \u003b也就是';'的Unicode代码
	 * 
	 * @param inStr
	 *            待转换为Unicode的等价字符串
	 * @return Unicode的字符串
	 */
	public static String toUnicodeString(String inStr) {
		char[] myBuffer = inStr.toCharArray();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < inStr.length(); i++) {
			short s = (short) myBuffer[i];
			String hexS = Integer.toHexString(s);
			sb.append(" \\u");
			sb.append(fillStr(hexS, "0", 4, AFTER));
		}
		return sb.toString();
	}

	/**
	 * 判断一个字符串是否是合法的Java标识符
	 * 
	 * @param s
	 *            待判断的字符串
	 * @return 如果参数s是一个合法的Java标识符返回真，否则返回假
	 */
	public static boolean isJavaIdentifier(String s) {
		if (s.length() == 0 || Character.isJavaIdentifierStart(s.charAt(0))) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isJavaIdentifierPart(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 替换字符串中满足条件的字符 例如: replaceAll("\com\hi\platform\base\\util",'\\','/');
	 * 返回的结果为: /com/hi/platform/base/util
	 * 
	 * @param str
	 *            待替换的字符串
	 * @param oldchar
	 *            待替换的字符
	 * @param newchar
	 *            替换为的字符
	 * @return 将str中的所有oldchar字符全部替换为newchar,并返回这个替换后的字符串
	 */
	public static String replaceAll(String str, char oldchar, char newchar) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == oldchar) {
				chars[i] = newchar;
			}
		}
		return new String(chars);
	}

	/**
	 * 如果inStr字符串与没有给定length的长度，则用fill填充，在指定direction的方向
	 * 如果inStr字符串长度大于length就直截返回inStr，不做处理
	 * 
	 * @param inStr
	 *            待处理的字符串
	 * @param fill
	 *            以该字符串作为填充字符串
	 * @param length
	 *            填充后要求的长度
	 * @param direction
	 *            填充方向，如果是AFTER填充在后面，否则填充在前面
	 * @return 返回一个指定长度填充后的字符串
	 */
	public static String fillStr(String inStr, String fill, int length,
			int direction) {
		if (inStr == null || inStr.length() > length || inStr.length() == 0) {
			return inStr;
		}

		StringBuilder tempStr = new StringBuilder("");
		for (int i = 0; i < length - inStr.length(); i++) {
			tempStr.append(fill);
		}

		if (direction == AFTER) {
			return new String(tempStr.toString() + inStr);
		} else {
			return new String(inStr + tempStr.toString());
		}
	}

	/**
	 * 字符串替换
	 * 
	 * @param str
	 *            源字符串
	 * @param pattern
	 *            待替换的字符串
	 * @param replace
	 *            替换为的字符串
	 * @return
	 */
	public static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuilder result = new StringBuilder();
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));

		return result.toString();
	}

	/**
	 * 分隔字符串到一个集合
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> splitToList(String str, String start, String end) {
		if (str == null || str.length() == 0) {
			return null;
		}

		int s = 0;
		int e = 0;
		List<String> result = new ArrayList<>();
		if (isIncludes(str, start) && isIncludes(str, end)) {
			while ((e = str.indexOf(start, s)) >= 0) {
				result.add(str.substring(s, e));
				s = str.indexOf(end, e) + end.length();
				result.add(str.substring(e, s));
			}
			if (s < str.length()) {
				result.add(str.substring(s));
			}

			if (s == 0) {
				result.add(str);
			}
		} else {
			result.add(str);
		}

		return result;
	}

	/**
	 * 首字母大写
	 * 
	 * @param string 字符串
	 * @return
	 */
	public static String upperFirst(String string) {
		if (string == null) {
			return null;
		}
		String upper = string.toUpperCase();
		return upper.charAt(0) + string.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param string 字符串
	 * @return
	 */
	public static String lowerFirst(String string) {
		if (string == null) {
			return null;
		}
		String lower = string.toLowerCase();
		return lower.charAt(0) + string.substring(1);
	}

	
	/**
	 * 
	 * 对URL编码
	 * 
	 * @param string 字符串
	 * @param encode 编码格式
	 * @return
	 */
	public static String urlEncode(String string, String encode) {
		try {
			return URLEncoder.encode(string, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 驼峰字符串转为下划线字符串 如：userTypeId => user_type_id
	 * @param str 字符串
	 * @return
	 */
	public static String camelToUnderline(String str){
		if (str==null||"".equals(str.trim())){
			return null;
		}
		int len=str.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=str.charAt(i);
			if (Character.isUpperCase(c)){
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下划线字符串转为驼峰字符串 如：user_type_id => userTypeId
	 * @param str 字符串
	 * @return
	 */
	public static String underlineToCamel(String str){
		if (StringUtils.isBlank(str)){
			return str;
		}
		StringBuilder sb=new StringBuilder(str);
		Matcher mc= Pattern.compile(UNDERLINE).matcher(str);
		int i=0;
		while (mc.find()){
			int position=mc.end()-(i++);
			sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 验证是否存在SQL注入代码(条件语句）
	 * @param str 字符串
	 * @return
	 */
	public static boolean hasSqlInjection(final String str) {
		// 里面定义恶意字符集合
		// 验证inputData是否包含恶意集合
		if(isNull(str)){
			return false;
		}
		final Pattern pattern = Pattern.compile(getSqlInjectionRegex());
		final Matcher matcher = pattern.matcher(str.trim().toLowerCase());
		final boolean b = matcher.matches();
		if (b) {
			log.info(String.format("检测出SQL注入的恶意数据, %s", str));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取SQL注入的判断正则表达式
	 * @return regexStr
	 */
	private static String getSqlInjectionRegex() {
		// 构造SQL的注入关键字符
		final String[] strBadChar =
				{"select\\s", "from\\s", "or\\s", "insert\\s", "delete\\s", "update\\s", "drop\\s", "truncate\\s",
						"exec\\s", "count\\(", "declare\\s", "asc\\(", "mid\\(", "char\\(", "net user", "xp_cmdshell", "/add\\s",
						"exec master.dbo.xp_cmdshell", "net localgroup administrators","and\\s","=\\s" ,"where\\s","<",">",
						"(?:')","(?:--)","\\*(?:.|[\\n\\r])*?\\*", "utl_http.request", "utl_inaddr."};

		// 构造正则表达式
		StringBuilder strRegex = new StringBuilder(".*(");
		for (int i = 0; i < strBadChar.length - 1; i++) {
			strRegex.append(strBadChar[i]).append("|");
		}
		strRegex.append(strBadChar[strBadChar.length - 1]).append(").*");

		return strRegex.toString();
	}

	/**
	 * 把数组合并为一个字符串
	 * @param text 数组
	 * @return 字符串
	 */
	public static String join(CharSequence... text){

		StringBuilder sb = new StringBuilder();
		for (CharSequence sequence : text) {
			if (null!=sequence){
				sb.append(sequence);
			}
		}
		return sb.toString();
	}
}
