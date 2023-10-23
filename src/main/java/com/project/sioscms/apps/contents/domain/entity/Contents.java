package com.project.sioscms.apps.contents.domain.entity;

import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.mapper.ContentsMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString
public class Contents extends CommonEntityWithIdAndDate {

    @Comment("콘텐츠명")
    private String contentName;

    @Comment("제목")
    private String title;

    @Column(columnDefinition = "TEXT")
    @Comment("본문")
    private String content;

    @ManyToOne
    private AttachFileGroup attachFileGroup;

    public ContentsDto.Response toResponse(){
        return ContentsMapper.mapper.toResponse(this);
    }
}
