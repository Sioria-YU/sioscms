package com.project.sioscms.common.utils.common.secure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class AesCryptoUtil {

    @Value("${aes.secret-key}")
    private String SECRET_KEY;

    @Value("${aes.iv-key}")
    private String IV_KEY;

    @Value("${aes.spec-name}")
    private String SPEC_NAME;


    //region 평문 암호화
    public static String encrypt(String inputText) throws Exception{
        //Null Or Empty Value Prevention
        if(inputText == null || inputText.trim().length() == 0){
            return null;
        }

        System.out.println("SECRET_KEY :::::>>>> " + SECRET_KEY);
        System.out.println("IV_KEY :::::>>>> " + IV_KEY);
        System.out.println("SPEC_NAME :::::>>>> " + SPEC_NAME);
        System.out.println("inputText :::::>> " + inputText);

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(IV_KEY.getBytes());
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = c.doFinal(inputText.getBytes("UTF-8"));

        //base64 encode
        String encryptString = new String(Base64.encodeBase64(encrypted));
        System.out.println("encryptString ::::::::>>> " + encryptString);

        return encryptString;
    }
    //endregion

    //region 평문 복호화
    public static String decrypt(String inputText) throws Exception{
        //Null Or Empty Value Prevention
        if(inputText == null || inputText.trim().length() == 0){
            return null;
        }

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(IV_KEY.getBytes());
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.decodeBase64(inputText);
        byte[] decrypted = c.doFinal(decodedBytes);
        String decryptString =  new String(decrypted, "UTF-8");

        System.out.println("decryptString ::::::::>>> " + decryptString);

        return decryptString;
    }
    //endregion

    //region 파일 암호화
    public static Boolean encryptFile(File attachFile, File createFile) throws Exception{
        return false;
    }
    //endregion

    //region 파일 복호화
    public static Boolean decryptFile(File encryptFile, File outputFile) throws Exception{
        return false;
    }
    //endregion

}
