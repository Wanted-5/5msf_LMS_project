package com.lms.global.util;

import java.security.SecureRandom;

public class InviteCodeUtil {

    private static final String CHARACTERS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generateVillageInviteCode(long cityId) {
        StringBuilder code = new StringBuilder();
        code.append("C").append(cityId).append("-"); // Prefix: C1- , C2- etc...

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }
}
