package com.example.sioscms;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class ObjectUtilsTest {

    @Test
    public void run(){
        List<String> stringList = Lists.newArrayList();
        List<String> stringList2 = null;

        System.out.println("stringList :::::> " + ObjectUtils.isEmpty(stringList));
        System.out.println("stringList2 :::::> " + ObjectUtils.isEmpty(stringList2));
    }
}
