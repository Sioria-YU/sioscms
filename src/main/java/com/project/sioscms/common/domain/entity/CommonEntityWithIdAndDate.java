package com.project.sioscms.common.domain.entity;

import com.project.sioscms.apps.account.domain.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CommonEntityWithIdAndDate {

    @Comment("고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //시퀀스 생성 규칙
    private Long id;

    @Comment("등록자 pk")
    @CreatedBy
    @ManyToOne
    private Account createdBy;

    @Comment("수정자 pk")
    @LastModifiedBy
    @ManyToOne
    private Account updatedBy;

    @Comment("등록일시")
    @CreatedDate
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(updatable = false)  //데이터 수정 방지
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Comment("수정일시")
    @LastModifiedDate
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updatedDateTime = LocalDateTime.now();
}
