package com.project.sioscms.apps.hashtag.domain.entity;

import com.project.sioscms.apps.board.domain.entity.Board;
import com.project.sioscms.common.domain.entity.CommonEntityWithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class BoardHashtag extends CommonEntityWithId {

    @ManyToOne(cascade = CascadeType.ALL)
    private Board board;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hashtag hashtag;

}
