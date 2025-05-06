package com.info.api.util;

public class StringUtil {

    /** Add padding before the input string. **/
    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }


    public static String capitalizeFirstLetter(String str) {
        try {
            if(str == null || str.isEmpty()) {
                return str;
            }

            return str.substring(0, 1).toUpperCase() + str.substring(1);

        } catch (Exception e) {
            return str;
        }

    }
}
