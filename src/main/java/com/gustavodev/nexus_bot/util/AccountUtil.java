package com.gustavodev.nexus_bot.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AccountUtil {

    public static String encrypt (String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String getTextByIndex(String text, int index) {
        return text.split(" ")[index];
    }

    public static String decrypt (String encryptedText) {
        return Arrays.toString(Base64.getDecoder().decode(encryptedText));
    }

}
