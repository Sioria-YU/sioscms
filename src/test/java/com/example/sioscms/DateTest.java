package com.example.sioscms;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateTest {

    @Test
    public void dateTest(){
        //웹훅테스트4
        LocalDate now = LocalDate.now();

        System.out.println("year ::: " + now.getYear());
        System.out.println("month ::: " + now.getMonthValue());
        System.out.println("day ::: " + now.getDayOfMonth());
    }
}
