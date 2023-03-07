package com.project.sioscms.apps.menu.domain.dto;

import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;

import java.time.LocalDateTime;

public class MenuDto {
    @Data
    public static class Request extends CommonSearchDto {
        private Long id;
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
        private String menuName;
        private Menu.MenuType menuType;
        private String menuLink;
        private Long upperMenuId;
        private Boolean isDeleted = false;
        private Boolean isUsed = true;
        private Boolean isRoot;
    }

    @Data
    public static class Response{
        private Long id;
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
        private String menuName;
        private Menu.MenuType menuType;
        private String menuLink;
        private Menu upperMenu;
        private Boolean isDeleted;
        private Boolean isUsed;
        private Boolean isRoot;
    }
}
