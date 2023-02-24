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
    private int pageNumber = 0;
    private int pageOffset = 10;
    private String sortedColumnName = null;
    private Sort.Direction direction = Sort.Direction.DESC;

    //검색 관련 컬럼
    private String keyword;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDate startDate;
    private LocalDate endDate;

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
    public Pageable toPageableWithSortedByCreatedOn(Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(this.pageNumber, this.pageOffset, Sort.by(direction, "createdOn"));
    }

    /**
     * 수정일 기준으로 정렬하는 페이징
     * @param direction
     * @return
     */
    public Pageable toPageableWithSortedByUpdatedOn(Sort.Direction direction){
        if(direction == null) direction = Sort.Direction.ASC;
        return PageRequest.of(this.pageNumber, this.pageOffset, Sort.by(direction, "updatedOn"));
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
