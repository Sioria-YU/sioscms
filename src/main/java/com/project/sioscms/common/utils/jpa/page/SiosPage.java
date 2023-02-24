package com.project.sioscms.common.utils.jpa.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class SiosPage<T> {
    //데이터, 페이징 정보를 나누어 view단에 전달하기 편하도록 변환함.
    private Page<T> page;
    private boolean isNext = false;
    private boolean isPrev = false;

    public SiosPage(Page<T> page) {
        this.page = page;

        //다음페이지, 이전페이지 존재 유무 설정
        /*if(page.getTotalElements() > 0){

        }*/
    }
}
