package org.cneko.ctlib.common.util.base;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /** 获取字符串中含有某个特定字符串的个数
     *
     * @param str 字符串
     * @param subStr 特定字符串
     * @return 个数
     */
    public static int getCount(String str, String subStr) {
        if (str == null || subStr == null || str.isEmpty() || subStr.isEmpty()) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }
        return count;
    }

    /**
     * 检查字符串是否符合格式
     *
     * @param original 原始字符串
     * @param format 格式，使用${any}表示任意字符
     * @return 是否符合格式
     */
    public static boolean checkFormat(String original, String format) {
        // 构造正则表达式
        String regex = format
                .replace("${any}", "(.*?)")
                .replace("${any}", "(.*?)"); // 匹配前缀和后缀中间的任意字符
        Pattern pattern = Pattern.compile("^" + regex + "$");

        // 使用正则表达式进行匹配
        Matcher matcher = pattern.matcher(original);
        return matcher.matches();
    }

    /**
     * 获取文本中的URL
     * @param text 文本
     * @return url列表
     */
    public static List<String> extractURLs(String text) {
        List<String> urls = new ArrayList<>();
        // 定义 URL 正则表达式模式
        String regex = "\\b(?:https?|HTTP|HTTPS)://\\S+\\b";
        // 编译正则表达式模式
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        // 创建匹配器对象
        Matcher matcher = pattern.matcher(text);

        // 寻找所有匹配的 URL
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        return urls;
    }
}
