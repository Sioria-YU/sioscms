package com.project.sioscms.common.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class CommonEntityWhidIdAndDate {

    @Comment("고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Comment("등록자 pk")
    @CreatedBy
    Long createdBy;

    @Comment("수정자 pk")
    @LastModifiedBy
    Long updatedBy;

    @Comment("등록일시")
    @CreatedDate
    LocalDateTime createdOn = LocalDateTime.now();

    @Comment("수정일시")
    @LastModifiedDate
    LocalDateTime updatedOn = LocalDateTime.now();
}
