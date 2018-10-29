package com.wxc.happyturtle.util;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StringUtil {

    public static String parseStringParam(String text) {
        if (StringUtils.isNotBlank(text) && !text.equals("null") && !text.equals("undefined")){
            text = text.replaceAll("\\<[^>]*>", "").trim();
            if(StringUtils.isNotBlank(text)){
                return text;
            }
        }
        return null;
    }

    public static Integer parseVersionParam(String v) {
        if (StringUtils.isNotBlank(v)) {
            v = v.replaceAll("\\.", "");
            if (StringUtils.isNotBlank(v)) {
                return parseIntegerParam(v);
            }
        }
        return null;
    }

    //转型输入的字符串为Integer
    public static Integer parseIntegerParam(String str) {
        String result = parseStringParam(str);
        if (result!=null) {
            return Integer.parseInt(result);
        }
        return null;
    }

    //转型输入的字符串为手机号
    public static String parsePhoneParam(String str) {
        String result = parseStringParam(str);
        if (result != null) {
            if(result.matches("^(13|14|15|16|17|18|19)[0-9]{9}$")){
                return result;
            }
        }
        return null;
    }

    public static Integer parseIntegerParam(String str,int defaultValue) {
        Integer result = parseIntegerParam(str);
        if (result==null) {
            return defaultValue;
        }
        return result;
    }

    //转型输入的字符串为BigDecimal
    public static BigDecimal parseBigDecimalParam(String str) {
        String result = parseStringParam(str);
        if (result!=null) {
            return new BigDecimal(result);
        }
        return null;
    }

    //检测第三方字符串是否为非法字符串
    public static boolean isNotNullParam(String text) {
        if (text==null || text.equalsIgnoreCase("null") || text.equalsIgnoreCase("undefined")){
            return false;
        }
        return true;
    }

    public static boolean isNullParam(String text) {
        return !isNotNullParam(text);
    }

    //替换字符串中的某个字段
    public static String replaceStringParam(String text,Integer i,String count) {
        String result = "";

        if(StringUtils.isNotBlank(text)){
            String[] filters=text.split("-");
            if(filters.length != 4){
                return result;
            }
            //将指定位置的值替换并拼接成新的字符串
            filters[i] = count;
            for(int n=0;n<filters.length;n++){
                result = result + filters[n];
                if(n != filters.length-1){
                    result = result + "-";
                }
            }
            return result;
        }

        return result;
    }

    //过滤emoji 或者 其他非文字类型的字符
    public static String removeEmoji(String s){
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isNotEmojiChar(c)) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    private static boolean isNotEmojiChar(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    // 字符串格式化拼接 code输入字符串 begin 起始位数。middle 中间加*号的位数，end 最后保留的位数
    public static String maskNumber(String code, int begin, int middle, int end) {
        StringBuilder sb = new StringBuilder();
        if (code != null && !code.trim().equals("")) {
            int length = code.length();
            sb.append(code.substring(0, begin));
            if (middle != 0) {
                for (int i = 1; i <= middle; i++) {
                    sb.append("*");
                }
            }
            sb.append(code.substring(length - end, length));
        }
        return sb.toString();
    }

    //字符串以符号分割，转化成HashSet
    public static HashSet<String> formatStringDivideByPunctuationToHashSet (String target,String punctuation) {
        HashSet<String> set = new HashSet<>();
        String[] array = target.split(punctuation);
        for (int i = 0; i < array.length ; i ++) {
            set.add(array[i]);
        }
        return set;
    }
    //字符串以符号分割，转化成list
    public static List formatStringDivideByPunctuationToList (String target,String punctuation1,String punctuation2) {
        List list_out = new ArrayList();
        String[] array_out = target.split(punctuation1);
        for (int i = 0; i < array_out.length ; i ++) {
            if (!punctuation2.equals("false")) {
                List list_in = new ArrayList();
                String[] array_in = array_out[i].split(punctuation2);
                for (int j = 0; j < array_in.length ; j ++) {
                    list_in.add(array_in[j]);
                }
                list_out.add(list_in);
            } else {
                list_out.add(array_out[i]);
            }

        }
        return list_out;
    }
    //字符串以符号分割，转化成Integer list
    public static List<Integer> formatStringDivideByPunctuationToIntList (String target,String punctuation) {
        List<Integer> list_out = new ArrayList<Integer>();
        String[] array_out = target.split(punctuation);
        for (int i = 0; i < array_out.length ; i ++) {
            list_out.add(Integer.parseInt(array_out[i]));

        }
        return list_out;
    }
}
