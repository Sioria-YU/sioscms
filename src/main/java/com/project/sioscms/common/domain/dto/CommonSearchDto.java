package com.project.sioscms.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommonSearchDto {
    //페이지 관련
    private int pageNumber = 1;     //현재 페이지 번호
    private int pageOffset = 10;    //한 페이지에 표시할 목록 수
    private int pageSize = 10;    //한 페이지에 표시할 목록 수
    private String sortedColumnName = null;
    private Sort.Direction direction = Sort.Direction.DESC;

    //검색 관련 컬럼
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    //응답 관련
    private String msg;

    /*private void setPageNumber(int pageNumber){
        //pageable의 page가 0부터 시작하므로 -1처리
        this.pageNumber = Math.max(pageNumber - 1, 0);
    }*/

    public int getPageNumber() {
        return Math.max(this.pageNumber - 1, 0);
    }

    /**
     * 정렬 없는 페이징
     * @return Pageable
     */
    public Pageable toPageable(){
        return PageRequest.of(getPageNumber(), this.pageOffset);
    }

    /**
     * 검색 조건에 따른 페이징과 정렬
     * @return Pageable
     */
    public Pageable toPageableWithSorted(){
        if(this.sortedColumnName != null && !this.sortedColumnName.isEmpty())
            return PageRequest.of(getPageNumber(), this.pageOffset, Sort.by(this.direction, this.sortedColumnName));
        else
            throw new NullPointerException();
    }

    /**
     * 등록일 기준으로 정렬하는 페이징
     * @param direction :ASC,DESC
     * @return Pageable
     */
    public Pageable toPageableWithSortedByCreatedDateTime(Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(getPageNumber(), this.pageOffset, Sort.by(direction, "createdDateTime"));
    }

    /**
     * 수정일 기준으로 정렬하는 페이징
     * @param direction :ASC,DESC
     * @return Pageable
     */
    public Pageable toPageableWithSortedByUpdatedDateTime(Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(getPageNumber(), this.pageOffset, Sort.by(direction, "updatedDateTime"));
    }

    /**
     * key를 인자로 받아 정렬하는 페이징
     * @param key :culumnName
     * @param direction :ASC,DESC
     * @return Pageable
     */
    public Pageable toPageableWithSortedByKey(String key, Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(getPageNumber(), this.pageOffset, Sort.by(direction, key));
    }

}
