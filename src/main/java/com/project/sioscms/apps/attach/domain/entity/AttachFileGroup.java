package com.project.sioscms.apps.attach.domain.entity;

import com.google.common.collect.Lists;
import com.project.sioscms.apps.attach.domain.dto.AttachFileGroupDto;
import com.project.sioscms.apps.attach.mapper.AttachFileGroupMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class AttachFileGroup extends CommonEntityWithIdAndDate {

    @ColumnDefault(value = "FALSE")
    @Comment("삭제여부")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "attachFileGroup")
    private List<AttachFile> attachFileList = Lists.newArrayList();

    public AttachFileGroupDto.Response toResponse() {return AttachFileGroupMapper.mapper.toResponse(this);}
}
