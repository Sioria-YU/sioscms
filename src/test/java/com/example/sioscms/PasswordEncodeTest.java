package com.example.sioscms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void encode(){
        String password = "1234";

        System.out.println("password ::::::: ");
        System.out.println(passwordEncoder.encode(password));

    }
}
