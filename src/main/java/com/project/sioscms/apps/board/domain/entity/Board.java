package com.project.sioscms.apps.board.domain.entity;

import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class Board extends CommonEntityWithIdAndDate {

    @ManyToOne
    private BoardMaster boardMaster;

    @Comment("제목")
    private String title;

    @Column(columnDefinition = "TEXT")
    @Comment("본문")
    private String content;

    @Column(columnDefinition = "TEXT")
    @Comment("본문 HTML태그 제거")
    private String contentWithoutHtml;

    //subContents 추가로 파야될듯
    //필요할 경우 BoardContent Entity 추가 생성

    @Comment("옵션1")
    private String option1;

    @Comment("옵션2")
    private String option2;

    @Comment("옵션3")
    private String option3;

    @Comment("옵션4")
    private String option4;

    @Comment("옵션5")
    private String option5;

    @ColumnDefault(value = "0")
    @Comment("조회수")
    private Long viewCount = 0L;

    @OneToOne
    @Comment("이미지파일 그룹 아이디")
    private AttachFileGroup imageFileGroup;

    @OneToOne
    @Comment("첨부파일 그룹 아이디")
    private AttachFileGroup attachFileGroup;

    //댓글 oneToMany 필요할 경우 추가
    //private List<Comment> commentList;

    @ColumnDefault(value = "FALSE")
    @Comment("삭제 여부")
    private Boolean isDeleted = false;

}
