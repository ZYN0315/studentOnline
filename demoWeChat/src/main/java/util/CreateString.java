package util;

import java.security.SecureRandom;

public class CreateString {
    private static final String TEXT = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String createString() {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 16; ++i) {
            sb.append(TEXT.charAt(random.nextInt(TEXT.length())));
        }
        return sb.toString();

    }
}
