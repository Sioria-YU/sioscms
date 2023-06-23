package com.project.sioscms.common.utils.common.secure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class AesCryptoUtil {

    //region 평문 암호화
    public static String encrypt(String secretKey, String ivKey, String specName, String inputText) throws Exception{
        //Null Or Empty Value Prevention
        if(inputText == null || inputText.trim().length() == 0){
            return null;
        }

        Cipher c = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = c.doFinal(inputText.getBytes("UTF-8"));

        //base64 encode
        return new String(Base64.encodeBase64(encrypted));
    }
    //endregion

    //region 평문 복호화
    public static String decrypt(String secretKey, String ivKey, String specName, String inputText) throws Exception{
        //Null Or Empty Value Prevention
        if(inputText == null || inputText.trim().length() == 0){
            return null;
        }

        Cipher c = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        c.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        //base64 decode
        byte[] decodedBytes = Base64.decodeBase64(inputText);
        byte[] decrypted = c.doFinal(decodedBytes);

        return new String(decrypted, "UTF-8");
    }
    //endregion

    //region 파일 암호화
    public static Boolean encryptFile(String secretKey, String ivKey, String specName, File attachFile, File createFile) throws Exception{
        return false;
    }
    //endregion

    //region 파일 복호화
    public static Boolean decryptFile(String secretKey, String ivKey, String specName, File encryptFile, File outputFile) throws Exception{
        return false;
    }
    //endregion

}
