package com.project.sioscms.common.utils.jpa.page;

import org.springframework.data.domain.Page;

public class PaginationUtil {
    /*TODO::
       1.Page<T> 인자를 받아 CustomPage 객체로 반환하는 기능 <- 굳이 필요할지 고려
       2.Static Method를 이용하여 CustomPage 객체 반환 -> PaginationUtil.of()
       3.반환 과정에서 ResponseDto가 있으면 변환
    *
    * */

    public static <T> SiosPage of(Page<T> pageData){
        return new SiosPage(pageData);
    }
}
