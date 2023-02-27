package com.project.sioscms.common.utils.jpa.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class SiosPage<T> {
    //데이터, 페이징 정보를 나누어 view단에 전달하기 편하도록 변환함.
    private Page<T> page;
    private boolean isNext = false;
    private boolean isPrev = false;
    private int pageSize = 10;   //한 페이지에 노출될 사이즈 -> 추후 설정 값 등으로 변경

    public SiosPage(Page<T> page) throws IllegalAccessException {
        if(page != null && !page.isEmpty()){
            this.page = page;
            this.setNextPrevPage();
        }else{
            throw new IllegalAccessException("Page<T> is Null!!!");
        }
    }

    /**
     * 한 페이지 표기 사이즈가 10이 아닐 경우 주입하여 사용
     * @param page
     * @param pageSize
     */
    public SiosPage(Page<T> page, int pageSize) throws IllegalAccessException {
        if(page != null && !page.isEmpty()) {
            this.page = page;
            this.pageSize = pageSize;
            this.setNextPrevPage();
        }else{
            throw new IllegalAccessException("Page<T> is Null!!!");
        }
    }

    /**
     * 이전/다음 페이지 유무 처리
     */
    private void setNextPrevPage(){
        //다음페이지, 이전페이지 존재 유무 설정
        if(this.page.getNumber() > 0 && this.page.getTotalPages() >= this.pageSize){
            if(this.page.getNumber()+1 < this.page.getTotalPages() - this.pageSize) {
                this.isNext = true;
            }else{
                this.isNext = false;
            }
        }
        if(this.page.getNumber()+1 > this.pageSize){
            this.isPrev = true;
        }
    }

    /**
     * 페이지 객체 데이터만 추출
     * @return
     */
    public List<T> getContents(){
        if(this.page.isEmpty()){
            return null;
        }else {
            return this.page.getContent();
        }
    }

    /**
     * 페이지 정보만 추출
     * @return
     */
    public PageResponseDto getPageInfo(){
        if(this.page.isEmpty()){
            return null;
        }else {
            return PageResponseDto.builder()
                    .totalCount(this.page.getTotalElements())
                    .totalPageSize(this.page.getTotalPages())
                    .pageNumber(this.page.getNumber())
                    .pageOffset(this.page.getSize())
                    .pageSize(this.pageSize)
                    .isNext(this.isNext)
                    .isPrev(this.isPrev)
                    .build();
        }
    }
}
