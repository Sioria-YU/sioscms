package com.project.sioscms.apps.code.domain.entity;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.mapper.CodeGroupMapper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CodeGroup {

    @Id
    @Comment("그룹코드 아이디")
    @Column(length = 100)
    private String codeGroupId;

    @NotNull
    @Comment("코드그룹 명")
    @Column(length = 100)
    private String codeGroupLabel;

    @Comment("코드그룹 설명")
    @Column(length = 1000)
    private String codeGroupNote;

    @Comment("삭제여부")
    @ColumnDefault(value = "FALSE")
    private Boolean isDeleted = false;

    @Comment("사용여부")
    @ColumnDefault(value = "TRUE")
    private Boolean isUsed = true;

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
    @Column(updatable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Comment("수정일시")
    @LastModifiedDate
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updatedDateTime = LocalDateTime.now();

    public CodeGroupDto.Response toResponse(){ return CodeGroupMapper.mapper.toResponse(this); }
}
