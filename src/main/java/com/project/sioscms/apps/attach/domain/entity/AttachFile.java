package com.project.sioscms.apps.attach.domain.entity;

import com.project.sioscms.apps.attach.domain.dto.AttachFileDto;
import com.project.sioscms.apps.attach.mapper.AttachFileMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class AttachFile  extends CommonEntityWithIdAndDate {

    @ManyToOne
    @Comment("첨부파일 그룹")
    private AttachFileGroup attachFileGroup;

    @Comment("파일명")
    private String fileName;

    @Comment("실제 파일명")
    private String originFileName;

    @Comment("파일 경로")
    private String filePath;

    @Comment("파일 사이즈")
    private Long fileSize;

    @Column(length = 10)
    @Comment("파일 확장자")
    private String fileExtension;

    @ColumnDefault(value = "0")
    @Comment("파일 순번")
    private Long fileOrder;

    @ColumnDefault(value = "FALSE")
    @Comment("삭제여부")
    private Boolean isDeleted = false;

    @ColumnDefault(value = "TRUE")
    @Comment("사용여부")
    private Boolean isUsed = true;

    public AttachFileDto.Response toResponse() {
        return AttachFileMapper.mapper.toResponse(this);
    }
}
