package com.project.sioscms.apps.menu.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

public class MenuDto {

//    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Request extends CommonSearchDto {
        private Long id;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
        private String menuName;
        private Menu.MenuType menuType;
        private String menuLink;
        private Long upperMenuId;
        private Boolean isDeleted = false;
        private Boolean isUsed = true;
        private Boolean isRoot;
        private Long orderNum;
    }

    @Data
    public static class Response{
        private Long id;
        private Account createdBy;
        private Account updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
        private String menuName;
        private Menu.MenuType menuType;
        private String menuLink;
        private Menu upperMenu;
        private Boolean isDeleted;
        private Boolean isUsed;
        private Boolean isRoot;
        private Long orderNum;
    }
}
