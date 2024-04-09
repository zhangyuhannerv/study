package com.zh.wordDemo;

import cn.hutool.core.util.NumberUtil;

public class MtoKxxx {
    public static void main(String[] args) {
        System.out.println(formatNumberToKPlusDecimal(4));
    }

    public static String formatNumberToKPlusDecimal(double number) {
        int a = (int) number / 1000;
        double v = number - a * 1000;
        String str = NumberUtil.roundStr(v, 2);
        String b = formatToEffectiveDigits(str);
        String res = "K" + a + "+" + b;
        return res;
    }

    public static String formatToEffectiveDigits(String numberStr) {
        // 分离整数部分和小数部分
        String[] parts = numberStr.split("\\.");
        String integerPart = parts[0]; // 整数部分
        String decimalPart = parts.length > 1 ? parts[1] : ""; // 小数部分

        // 去除小数部分末尾的零
        decimalPart = decimalPart.replaceFirst("0+$", "");
        if (decimalPart.isEmpty()) {
            // 如果没有小数部分，则不需要点号
            return integerPart;
        }

        // 返回整合后的字符串
        return integerPart + "." + decimalPart;
    }
}
