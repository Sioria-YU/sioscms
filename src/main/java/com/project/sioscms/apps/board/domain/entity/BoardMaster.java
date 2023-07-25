package com.project.sioscms.apps.board.domain.entity;

import com.project.sioscms.apps.board.domain.dto.BoardMasterDto;
import com.project.sioscms.apps.board.mapper.BoardMasterMapper;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class BoardMaster extends CommonEntityWithIdAndDate {

    @NotNull
    @Comment("게시판 이름")
    private String boardName;

    @OneToOne
    @NotNull
    @Comment("게시판 유형 코드")
    private Code boardTypeCode;

    @ColumnDefault(value = "FALSE")
    @Comment("삭제여부")
    private Boolean isDeleted = false;

    public BoardMasterDto.Response toResponse() {return BoardMasterMapper.mapper.toResponse(this);}
}
