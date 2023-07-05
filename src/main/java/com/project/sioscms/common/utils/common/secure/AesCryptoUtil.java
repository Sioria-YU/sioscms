package com.project.sioscms.common.utils.common.secure;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AesCryptoUtil {

    //region 평문 암호화
    public static String encrypt(String secretKey, String ivKey, String specName, String inputText) throws Exception{
        //Null Or Empty Value Prevention
        if(inputText == null || inputText.trim().length() == 0){
            return null;
        }

        //Cipher 초기 값 주입
        Cipher cipher = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(inputText.getBytes(StandardCharsets.UTF_8));

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

        //Cipher 초기 값 주입
        Cipher cipher = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        //base64 decode
        byte[] decodedBytes = Base64.decodeBase64(inputText);
        byte[] decrypted = cipher.doFinal(decodedBytes);

        return new String(decrypted, StandardCharsets.UTF_8);
    }
    //endregion

    //region 파일 암호화
    public static Boolean encryptFile(String secretKey, String ivKey, String specName, File attachFile, File createFile) throws Exception{
        //Cipher 초기 값 주입
        Cipher cipher = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        try(
                //암호화할 파일을 읽을 스트림 객체 생성
                FileInputStream fileInputStream = new FileInputStream(attachFile);
                // 암호화된 내용을 출력할 스트림 객체 생성
                FileOutputStream fileOutputStream = new FileOutputStream(createFile);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher))
        {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                // 암호화된 내용을 출력 파일에 작성
                cipherOutputStream.write(buffer, 0, bytesRead);
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //endregion

    //region 파일 복호화
    public static Boolean decryptFile(String secretKey, String ivKey, String specName, File encryptedFile, HttpServletResponse response) throws Exception{
        //Cipher 초기 값 주입
        Cipher cipher = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        try (
                //암호화 파일을 읽어들일 스트림 객체 생성
                FileInputStream inputStream = new FileInputStream(encryptedFile);
                CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                // 복호화된 내용을 출력 파일에 작성
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            response.getOutputStream().flush();
            response.getOutputStream().close();

            inputStream.close();
            cipherInputStream.close();

            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }
    //endregion

}
