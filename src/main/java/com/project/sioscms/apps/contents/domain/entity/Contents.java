package com.project.sioscms.apps.contents.domain.entity;

import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.mapper.ContentsMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
public class Contents extends CommonEntityWithIdAndDate {

    @NotNull
    @Comment("콘텐츠명")
    private String contentsName;

    @NotNull
    @Comment("제목")
    private String title;

    @Column(columnDefinition = "TEXT")
    @Comment("본문")
    private String content;

    @Comment("첨부파일(이미지)")
    @ManyToOne
    private AttachFileGroup attachFileGroup;

    @ColumnDefault(value = "FALSE")
    @Comment("삭제 여부")
    private Boolean isDeleted = false;

    public ContentsDto.Response toResponse(){
        return ContentsMapper.mapper.toResponse(this);
    }
}
