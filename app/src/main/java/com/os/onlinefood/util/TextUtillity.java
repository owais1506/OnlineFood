package com.os.onlinefood.util;

import android.graphics.Color;
import android.text.TextUtils;

import java.util.Locale;
import java.util.Random;

public class TextUtillity {

    public static String firstText(String data) {
        if (!TextUtils.isEmpty(data) && !data.equalsIgnoreCase("null")) {
            String firsChar = String.valueOf(data.charAt(0));
            return firsChar.toUpperCase();
        } else {
            return "";
        }
    }



    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }






    public static String convertToString(int data) {

        String firsChar = String.valueOf(data);
        return firsChar;
    }


}
