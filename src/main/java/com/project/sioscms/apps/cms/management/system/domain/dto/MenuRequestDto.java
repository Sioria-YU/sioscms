package com.project.sioscms.apps.cms.management.system.domain.dto;

import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDto extends MenuDto.Request{
    Long menuId;

}
