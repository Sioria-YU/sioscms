package com.example.sioscms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("passwordEncode 암호화 결과 확인용")
    public void encode(){
        String password = "1234";

        System.out.println("password ::::::: ");
        System.out.println(passwordEncoder.encode(password));

    }

}
