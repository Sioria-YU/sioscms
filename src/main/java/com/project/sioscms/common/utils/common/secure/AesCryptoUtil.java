package com.project.sioscms.common.utils.common.secure;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
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

        Cipher cipher = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        try(FileInputStream fileInputStream = new FileInputStream(attachFile);
            FileOutputStream fileOutputStream = new FileOutputStream(createFile))
        {
            byte[] fileBytes = new byte[1024];
            byte[] output = new byte[cipher.getOutputSize(fileBytes.length)];
            int procLength = 0;
            int read = -1;

            while ((read = fileInputStream.read(fileBytes)) != -1){
                procLength = cipher.update(fileBytes, 0, fileBytes.length, output, 0);
            }

            procLength = cipher.doFinal(output, procLength);
            fileOutputStream.write(output);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //endregion

    //region 파일 복호화
    public static Boolean decryptFile(String secretKey, String ivKey, String specName, File encryptFile, File outputFile) throws Exception{
        Cipher c = Cipher.getInstance(specName);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(ivKey.getBytes());
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        try (
                CipherInputStream cipherInput = new CipherInputStream(new FileInputStream(encryptFile), c);
                InputStreamReader inputStream = new InputStreamReader(cipherInput);
                BufferedReader reader = new BufferedReader(inputStream);
                FileOutputStream fileOutput = new FileOutputStream(outputFile)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            fileOutput.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            return false;
        }
        return true;
    }
    //endregion

}
