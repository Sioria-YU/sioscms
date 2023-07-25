package com.project.sioscms.apps.hashtag.domain.entity;

import com.project.sioscms.apps.hashtag.domain.dto.HashtagDto;
import com.project.sioscms.apps.hashtag.mapper.HashtagMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Hashtag extends CommonEntityWithIdAndDate {

    @NotNull
    @Column(unique=true)
    @Comment("해시태그 명")
    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    @ToString.Exclude
    private Set<BoardHashtag> boardHashtagSet;

    @Comment("삭제 여부")
    @ColumnDefault(value = "FALSE")
    private Boolean isDeleted = false;

    @Comment(value = "노출 여부")
    @ColumnDefault(value = "TRUE")
    private Boolean isDisplay = true;

    public HashtagDto.Response toResponse(){return HashtagMapper.mapper.toResponse(this);}

}
