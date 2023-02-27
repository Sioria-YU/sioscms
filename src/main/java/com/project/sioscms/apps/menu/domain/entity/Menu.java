package com.project.sioscms.apps.menu.domain.entity;

import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Menu extends CommonEntityWithIdAndDate {
    public enum MenuType{
        LINK,
        BOARD,
        PROGRAM
    }

    @Comment(value = "메뉴명")
    @Column(nullable = false)
    private String menuName;

    @Comment(value = "메뉴 타입")
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'LINK'")
    private MenuType menuType;

    @Comment(value = "메뉴 링크")
    private String menuLink;

    @Comment(value = "상위 메뉴 아이디")
    @ManyToOne
    private Menu upperMenu;

    @Comment(value = "삭제 여부")
    @ColumnDefault(value = "FALSE")
    private Boolean isDeleted = false;

    @Comment(value = "사용 여부")
    @ColumnDefault(value = "TRUE")
    private Boolean isUsed = true;

}
