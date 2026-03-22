package com.lms.global.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    /**
     * 평문 비밀번호를 SHA-256 알고리즘을 사용하여 해싱한다.
     * 처음 써보는 로직이여서 틀릴 가능성 있습니다! 강사님한테 검수 받기
     * @param password 평문 비밀번호
     * @return 64자리의 16진수 해시 문자열
     */

    public static String hash(String password) {

        try {

            // SHA-256 해시 알고리즘 인스턴스 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 평문 비밀번호 -> 바이트 배열로 변환해서 md에 업데디트
            md.update(password.getBytes());

            // 해싱 시작
            byte[] byteData = md.digest();

            // 바이트 배열을 16진수 문자열(Hex String)로 변환
            StringBuilder sb = new StringBuilder();

            for (byte b : byteData) {

                // why? byte는 -128 ~ 127인데 이걸 그대로 문자열로 바꾸면 이상한 글씨 나오니까?
                // 02x: 1바이트를 2자리의 16진수로 포맷팅해서 (빈자리는 0으로 채움)
                // 가변형 빌더 배열에 하나씩 추가
                sb.append(String.format("%02x", b));
            }

            // 배열을 문자열로 다시 변환해서 리턴
            return sb.toString();


        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("해싱 알고리즘을 찾을 수 없습니다.", e);
        }
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {

        // 평문 패스워드를 -> 암호화 하기, 즉 사용자 입력 값을 암호화하여 담음
        String plainToHashed = hash(plainPassword);

        // 검증
        return plainToHashed.equals(hashedPassword);
    }
}
