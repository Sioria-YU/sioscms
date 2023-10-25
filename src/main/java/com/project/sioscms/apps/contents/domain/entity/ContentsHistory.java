package com.project.sioscms.apps.contents.domain.entity;

import com.project.sioscms.apps.contents.domain.dto.ContentsHistoryDto;
import com.project.sioscms.apps.contents.mapper.ContentsHistoryMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentsHistory extends CommonEntityWithIdAndDate {

    @NotNull
    @ManyToOne
    private Contents contents;

    @Column(columnDefinition = "TEXT")
    @Comment("본문")
    private String content;

    @ColumnDefault(value = "1")
    @Comment("버전")
    private Long version;

    @ColumnDefault(value = "TRUE")
    @Comment("사용 여부")
    private Boolean isUsed = true;

    @ColumnDefault(value = "FALSE")
    @Comment("삭제 여부")
    private Boolean isDeleted = false;

    public ContentsHistoryDto.Response toResponse(){return ContentsHistoryMapper.mapper.toResponse(this);
    }
}
