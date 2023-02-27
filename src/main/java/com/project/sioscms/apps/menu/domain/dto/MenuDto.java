package com.project.sioscms.apps.menu.domain.dto;

import com.project.sioscms.apps.menu.domain.entity.Menu;
import lombok.Data;

import java.time.LocalDateTime;

public class MenuDto {
    @Data
    public class Request{
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
    }

    @Data
    public class Response{
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
    }
}
