/**
 * Copyright(c) 2010-2013 by XiangShang Inc.
 * All Rights Reserved
 */
package com.xs.micro.check.invoicing.extension.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class ValidateUtil {

	private ValidateUtil() {
	}

	public static ValidateUtil getInstance() {
		return new ValidateUtil();
	}
	
	/**
	 * 是否为邮箱
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean isMail(String mail) {
		if (!isEmpty(mail)) {
			String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
			return matText(regex, mail);
		}
		return false;
	}

	/**
	 * 是否为手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		if (!isEmpty(mobile)) {
			String regex = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
			return matText(regex, mobile);
		}
		return false;
	}

	/**
	 * 是否为数字, 只允许为整形
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNum(String num) {
		if (!isEmpty(num)) {
			String regex = "[0-9]*";
			return matText(regex, num);
		}
		return false;
	}

	/**
	 * @Author WangQing
	 * @MethodName isWeiXin
	 * @Description 验证是否是微信账号
	 * @param content
	 * @Return boolean
	 * @Throw
	 * @Date 2018/11/22 18:25
	 */
	public static boolean isWeiXin(String content){
		if (!isEmpty(content)) {
			String regex = "[-_a-zA-Z0-9]*";
			return matText(regex, content);
		}
		return false;
	}



	/**
	 * @Author WangQing
	 * @MethodName containEmoji
	 * @Description 判断字符串中是否包含Emoji表情符号
	 * @param content
	 * @Return boolean
	 * @Throw
	 * @Date 2018/11/20 10:31
	 */
	public static boolean containEmoji(String content){

		if(content == null){
			return false;
		}
		Pattern pattern = compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
		Matcher matcher = pattern.matcher(content);
		if(matcher .find()){
			return true;
		}
		return false;
	}

	/***
	 * @Author WangQing
	 * @MethodName containGreaterThanFourBytes
	 * @Description 判断字符串中是否包含大于等于4个字节的特殊字符（因为MySQL中的utf-8并不是真正意义上的utf-8，它只能存储1~3个字节长度的utf-8编码，如果字符大于等于4个字节，sql会报错）
	 * @param content
	 * @Return boolean
	 * @Throw
	 * @Date 2018/11/23 11:12
	 */
	public static boolean containGreaterThanFourBytes(String content){
		if(content == null){
			return false;
		}
		Pattern pattern = compile("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]");
		Matcher matcher = pattern.matcher(content);
		if(matcher .find()){
			return true;
		}
		return false;
	}

	/**
	 * 是否为数字 包括整形，小数，并且必须含几位小数 0 表示不含小数
	 * @param num
	 * @param diag
	 * @return
	 */
	public static boolean isNum(String num, int diag) {
		int index = num.indexOf(".");
		if (index < 0 || diag == 0) {
			return isNum(num);
		} else {
			String num1 = num.substring(0, index);
			String num2 = num.substring(index + 1);
			if (num2.length() > diag) {
				return false;
			}
			return isNum(num1) && isNum(num2);
		}
	}

	/**
	 * 验证是否为数字，并且该数字取值范围合法，包含小数位
	 * 
	 * @param num
	 * @param min
	 * @param max
	 * @param diag
	 *            小数位 0 表示无小数
	 * @return
	 */
	public static boolean isNum(String num, double min, double max, int diag) {
		if (!isNum(num, diag)) {
			return false;
		}
		if (Double.valueOf(num).doubleValue() < min) {
			return false;
		}
		if (Double.valueOf(num).doubleValue() > max) {
			return false;
		}
		return true;
	}

	/**
	 * 验证传入参数，是否位于集合内
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isInArray(String param, String[] params) {
		if (isEmpty(param)) {
			return false;
		}
		for (String p : params) {
			if (param.equals(p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串是否小于传入长度
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isInLength(String str, int min, int max) {
		if (!isEmpty(str)) {
			if (str.length() >= min && str.length() <= max) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否为日期格式 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDate(String date) {
		if (!isEmpty(date)) {
			String dp1 = "\\d{4}-\\d{2}-\\d{2}";
			String dp2 = "^((\\d{2}(([02468][048])|([13579][26]))"
					+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
					+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
					+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
					+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
					+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

			if (matText(dp1, date)) {
				if (matText(dp2, date)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						sdf.parse(date);
						return true;
					} catch (ParseException e) {
						return false;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为布尔类型字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBoolean(String str) {
		if (!isEmpty(str)) {
			if ("true".equals(str.toLowerCase())
					|| "false".equals(str.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否存在于枚举类型内
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static boolean isInEnum(String str, Class<?> c) {
		if (!isEmpty(str)) {
			for (Object o : c.getEnumConstants()) {
				if (o.toString().equals(str)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean matText(String expression, String text) {
		Pattern p = compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isInEnumName(String str, Class<?> c) {
		if (!isEmpty(str)) {
			for (Object o : c.getEnumConstants()) {
				if (((Enum) o).name().equals(str)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId
				.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}


	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	private static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/**
	 * 验证是否是邮政编码
	 * 
	 * @param postCode
	 * @return
	 */
	public static boolean isPostCode(String postCode) {

		if (isEmpty(postCode)) {
			return false;
		}

		boolean retval = false;
		String zipCodePattern = "\\p{Digit}{6}";
		retval = postCode.matches(zipCodePattern);
		return retval;
	}

	/**
	 * 校验支付密码
	 * 
	 * @param password
	 * @return
	 */
	public static boolean isPayPwd(String password) {
		if (StringUtils.isEmpty(password)) {
			return false;
		}
		Pattern p = compile("^[0-9]{6}$");
		Matcher m = p.matcher(password);
		boolean b = m.matches();
		return b;
	}
	/**
	 * 是否含有中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
        Pattern p = compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	/**
	 * 登陆密码
	 * @param str
	 * @return
	 */
	public static boolean isPwd(String str) {
		
		if(StringUtils.isEmpty(str)){
			return false;
		}
		if(str.contains(" ")){
			return false;
		}
		Pattern p = compile("^[a-zA-Z0-9]{6,12}$");
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        }
        return true;
    }
	
	/**
	 * 是否是向上合法的昵称4-32个长度的汉字字母数字
	 * @param nickName
	 * @return
	 */
	public static boolean isLegalNickName(String nickName) {
		if(StringUtils.isEmpty(nickName)){
			return false;
		}
		return matText("^([\u4E00-\u9FA5]|\\w){4,32}$", nickName);
	}

}
