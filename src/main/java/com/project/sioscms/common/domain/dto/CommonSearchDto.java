package com.project.sioscms.common.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommonSearchDto {
    //페이지 관련
    private int pageNumber = 0;     //현재 페이지 번호
    private int pageOffset = 10;    //한 페이지에 표시할 목록 수
    private String sortedColumnName = null;
    private Sort.Direction direction = Sort.Direction.DESC;

    //검색 관련 컬럼
    private String keyword;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDate startDate;
    private LocalDate endDate;

    //응답 관련
    private String msg;

    private void serPageNumber(int pageNumber){
        //pageable의 page가 0부터 시작하므로 -1처리
        this.pageNumber = Math.max(pageNumber - 1, 0);
    }

    /**
     * 정렬 없는 페이징
     * @return
     */
    public Pageable toPageable(){
        return PageRequest.of(this.pageNumber, this.pageOffset);
    }

    /**
     * 검색 조건에 따른 페이징과 정렬
     * @return
     */
    public Pageable toPageableWithSorted(){
        if(this.sortedColumnName != null && !this.sortedColumnName.isEmpty())
            return PageRequest.of(this.pageNumber, this.pageOffset, Sort.by(this.direction, this.sortedColumnName));
        else
            throw new NullPointerException();
    }

    /**
     * 등록일 기준으로 정렬하는 페이징
     * @param direction
     * @return
     */
    public Pageable toPageableWithSortedByCreatedDateTime(Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(this.pageNumber, this.pageOffset, Sort.by(direction, "createdDateTime"));
    }

    /**
     * 수정일 기준으로 정렬하는 페이징
     * @param direction
     * @return
     */
    public Pageable toPageableWithSortedByUpdatedDateTime(Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(this.pageNumber, this.pageOffset, Sort.by(direction, "updatedDateTime"));
    }

    /**
     * key를 인자로 받아 정렬하는 페이징
     * @param key
     * @param direction
     * @return
     */
    public Pageable toPageableWithSortedByKey(String key, Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(this.pageNumber, this.pageOffset, Sort.by(direction, key));
    }

}
