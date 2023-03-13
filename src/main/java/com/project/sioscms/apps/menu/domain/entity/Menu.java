package com.project.sioscms.apps.menu.domain.entity;

import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.mapper.MenuMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Cacheable  //캐시 사용
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)   //캐시 전략:읽기 쓰기
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu upperMenu;

    @Comment(value = "삭제 여부")
    @ColumnDefault(value = "FALSE")
    private Boolean isDeleted = false;

    @Comment(value = "사용 여부")
    @ColumnDefault(value = "TRUE")
    private Boolean isUsed = true;

    @Comment(value = "루트 여부")
    @ColumnDefault(value = "FALSE")
    private Boolean isRoot = false;

    @NotNull
    @Comment(value = "정렬 순서")
    @Column(nullable = false)
    @ColumnDefault(value = "0")
    private Long orderNum = 0L;

    public MenuDto.Response toResponse(){
        return MenuMapper.mapper.toResponse(this);
    }
}
