package com.lixy.boothigh.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc:正则工具类
 * @Author:li_shuai
 * @date:Create on 2017/9/30 17:29
 */
public class RegexUtils {


    private static String MOBILE_REGEX = "^(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$";

    //验证手机号以1开头，总11位
    private static String MOBILE_NUM_REGEX = "^([1-9]{1})(\\d{10})$";
    //验证身份证位数15或18
    private static String ID_CARD_REGEX = "^\\d{15}$|^\\d{17}([0-9]|x|X)$";
    //车牌号正则表达式
    private static String CAR_NUM_REGEX = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";

    private static String EMAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    private static String BANK_NO_REGEX = "^([1-9]{1})(\\d{11,18})$";

    private static String ALL_NUM_REGEX = "^[0-9]+$";
    //两位小数有效位的数字校验
    private static String TWO_DECIMAL_REGEX = "(^[1-9](\\d+)?(\\.\\d{1,2})?$)|(^(0){1}$)|(^\\d\\.\\d{1,2}?$)";
    //验证8为以内的整数
    private static String INTEGER_NUM_8_REGEX = "(^[1-9]{1}([0-9]{1,7})?$)|(^(0){1}$)";
    //验证8位以内的整数 不包含0
    private static String INTEGER_NUM_8_Ex0_REGEX = "^[1-9]{1}([0-9]{1,7})?$";
    //验证全是float
    private static String FLOAT_REGEX = "^[0-9]+([.]{1}[0-9]+){0,1}$";
    //日期格式，2014-01-01或2014-01-01 12:00:00
    private static String DATE_REGEX = "(^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$)|(^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$)";
    //验证日期后的格式为2014-01-01 15:05:29.0
    private static String TIMESTAMP_REGEX = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d(\\.\\d)$";



    public static boolean validate(String context,String pattern) {
        Pattern pa = Pattern.compile(pattern);
        Matcher ma = pa.matcher(context);
        while (ma.find()) {
            return true;
        }
        return false;
    }

    /**
     * 手机号正则验证
     * @param str
     * @return
     */
    public static boolean  validateMobile(String str) {
        return validate(str, MOBILE_REGEX);
    }
    /**
     * 简易手机号验证，只验证位数
     * @param str
     * @return
     */
    public static boolean  validateMobileNum(String str) {
        return validate(str, MOBILE_NUM_REGEX);
    }
    /**
     * 简易身份证验证，只验证位数
     * @param str
     * @return
     */
    public static boolean  validateIdCard(String str) {
        return validate(str, ID_CARD_REGEX);
    }
    /**
     * 验证车牌号
     * @param str
     * @return
     */
    public static boolean  validateCarNumber(String str) {
        return validate(str, CAR_NUM_REGEX);
    }
    /**
     * 邮箱正则验证
     * @param str
     * @return
     */
    public static boolean  validateEmail(String str) {
        return validate(str, EMAIL_REGEX);
    }

    /**
     * 银行卡正则校验
     * @param str
     * @return
     */
    public static boolean validateBankNo(String str) {
        return validate(str, BANK_NO_REGEX);
    }

    /***
     * 纯数字校验
     * @param str
     * @return
     */
    public static boolean validateAllNumbers(String str) {
        return validate(str, ALL_NUM_REGEX);
    }

    /***
     * 两位小数有效位
     * @param str
     * @return
     */
    public static boolean validateTwoDecimal(String str) {
        return validate(str, TWO_DECIMAL_REGEX);
    }
    /***
     * 8位以内的正整数包含0
     * @param str
     * @return
     */
    public static boolean validateIntegerFor8(String str) {
        return validate(str, INTEGER_NUM_8_REGEX);
    }
    /***
     * 8为以内的正整数不包含0
     * @param str
     * @return
     */
    public static boolean validateIntegerFor8ExZero(String str) {
        return validate(str, INTEGER_NUM_8_Ex0_REGEX);
    }
    /**
     * float
     * @param str
     * @return
     */
    public static boolean validateFloat(String str){
        return validate(str, FLOAT_REGEX);
    }
    /**
     * date
     * @param str
     * @return
     */
    public static boolean validateDate(String str){
        return validate(str, DATE_REGEX);
    }

    /**
     * 验证时间日期后的时间格式 2014-01-01 15:05:29.0
     * @param str
     * @return
     */
    public static boolean validateTimestamp(String str){
        return validate(str, TIMESTAMP_REGEX);
    }
    public static void main(String[] args) {
        System.out.println(RegexUtils.validateTimestamp("2014-01-01 15:05:29.0"));
    }
}
