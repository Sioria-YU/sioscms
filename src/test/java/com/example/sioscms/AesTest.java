package com.example.sioscms;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesTest {
    //AES256은 256bit=32byte의 암호화 키가 필요함.
    private final String key = "dhtkdwnsdi199503291045dhtkdwns12";
    private final String iv = key.substring(0, 16); // 16byte의 초기화벡터값

    @Test
    public void encryptTest() throws Exception {
        //암호화 모드는 CBC를, 패딩은 PKCS5을 사용
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        String plainText = "testtesttesttest12341234";

        byte[] encrypted = c.doFinal(plainText.getBytes("UTF-8"));
        //base64로 인코딩
        String encryptString = new String(Base64.encodeBase64(encrypted));

        System.out.println("encryptString ::::>>>> " + encryptString);
        //4HMinwbB7vmXvJK/8kNhzCM9Z8k4q/Cg0yvaXlhhqJE=
    }

    //복호화메소드
    @Test
    public void decryptTest() throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        c.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        String cipherText = "4HMinwbB7vmXvJK/8kNhzCM9Z8k4q/Cg0yvaXlhhqJE=";

        byte[] decodedBytes = Base64.decodeBase64(cipherText);
        byte[] decrypted = c.doFinal(decodedBytes);
        String decryptString =  new String(decrypted, "UTF-8");

        System.out.println("decryptString ::::>>>> " + decryptString);
    }
}
