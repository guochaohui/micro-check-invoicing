package com.xs.micro.check.invoicing.extension.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * 正则工具类
 * @author guochaohui
 */
public class RegexUtil {
 
    /**
     * 删除STYLE代码
     * 
     * @param str
     *            HTML字符串
     * @return
     */
    public static String deleteStyleStr(String str) {
        Pattern p = compile("<style[^>]*>[\\d\\D]*?</style>", CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
 
    /**
     * 删除SCRIPT代码
     * 
     * @param str
     *            HTML字符串
     * @return
     */
    public static String deleteScriptStr(String str) {
        Pattern p = compile("<script[^>]*>[\\d\\D]*?</script>", CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
 
    /**
     * 删除HTML标签
     * 
     * @param str
     *            HTML字符串
     * @return
     */
    public static String deleteHtmlStr(String str) {
        Pattern p = compile("</?[^>]+/?>", CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
 
    /**
     * 替换HTML标签为指定字符
     * 
     * @param str
     *            HTML字符串
     * @param repStr
     *            替换的字符
     * @return
     */
    public static String replaceHtmlStr(String str, String repStr) {
        Pattern p = compile("</?[^>]+/?>", CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.replaceAll(repStr);
    }
 
    /**
     * 删除以&开头;结尾的字符
     * 
     * @param str
     *            HTML字符串
     * @return
     */
    public static String deleteNbspStr(String str) {
        Pattern p = compile("&\\w+;", CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
 
    /**
     * 删除空白字符
     * 
     * @param str
     *            HTML字符串
     * @return
     */
    public static String deleteSpaceStr(String str) {
        Pattern p = compile("[　\\s]+", CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
 
    /**
     * 根据正则表达式获取数据
     * 
     * @param regexStr
     *            正则表达式
     * @param htmlStr
     *            HTML字符串
     * @param unWantedStr
     *            获取的结果字符串中不需要的字符 ，请用空格分隔“, | -”
     * @return 返回的数据会根据正则表达式分组使用|进行拼接
     */
    public static List<String> getDetailDataByRegex(String regexStr, String htmlStr, String unWantedStr) {
        String[] unWantedStrs = unWantedStr.split("\\s+");
        List<String> listData = new ArrayList<String>();
        Pattern pattern = compile(regexStr);
        Matcher matcher = pattern.matcher(htmlStr);
        while (matcher.find()) {
            String str = "";
            for (int i = 0; i < matcher.groupCount(); i++) {
                String tmp = matcher.group(i + 1);
                tmp = tmp == null ? "" : tmp;
                for (String uwStr : unWantedStrs) {
                    tmp = tmp.replaceAll(uwStr, "");
                }
                str += str.equals("") == true ? tmp : "|" + tmp;
            }
            listData.add(str);
        }
        return listData;
    }
 
    /**
     * 匹配正则和字符串
     * 
     * @author Liyipeng
     * @param repexpStr
     *            正则表达式
     * @param contentStr
     *            要比较的字符串
     * @return 匹配成功返回true，匹配不成功返回false
     */
    public static Boolean compareStr(String repexpStr, String contentStr) {
        Pattern pattern = compile(repexpStr, CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(contentStr);
        return matcher.find();
    }
 
    /**
     * 取出纯文本，去除html，css，script，空格
     * 
     * @param htmlStr
     * @return text
     */
    public static String getClearText(String htmlStr) {
        htmlStr = deleteStyleStr(htmlStr);
        htmlStr = deleteScriptStr(htmlStr);
        htmlStr = deleteHtmlStr(htmlStr);
        htmlStr = deleteNbspStr(htmlStr);
        htmlStr = deleteSpaceStr(htmlStr);
        return htmlStr;
    }
 
    /**
     * 去掉css,script, 把html替换成|
     * 
     * @param htmlStr
     * @return text
     */
    public static String getTextFromHtml(String htmlStr) {
        htmlStr = deleteStyleStr(htmlStr);
        htmlStr = deleteScriptStr(htmlStr);
        htmlStr = deleteNbspStr(htmlStr);
        htmlStr = deleteSpaceStr(htmlStr);
        htmlStr = replaceHtmlStr(htmlStr, "\\|");
        htmlStr = deleteSymbol(htmlStr);
        return htmlStr;
    }
 
    /**
     * 去除特殊符号
     * 
     * @param htmlStr
     * @return text
     */
    private static String deleteSymbol(String htmlStr) {
        htmlStr = htmlStr.replaceAll(",", "");
        htmlStr = htmlStr.replaceAll(">", "");
        htmlStr = htmlStr.replaceAll("<", "");
        htmlStr = htmlStr.replaceAll("◆", "");
        htmlStr = htmlStr.replaceAll("【", "");
        htmlStr = htmlStr.replaceAll("】", "");
        htmlStr = htmlStr.replaceAll("\\+", "");
        htmlStr = htmlStr.replaceAll("\\=", "");
        htmlStr = htmlStr.replaceAll("！", "");
        htmlStr = htmlStr.replaceAll("/", "-");
        return htmlStr;
    }
 
}