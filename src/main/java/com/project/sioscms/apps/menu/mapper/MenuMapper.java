package com.project.sioscms.apps.menu.mapper;

import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuMapper extends CommonEntityMapper<Menu, MenuDto.Request, MenuDto.Response> {
    MenuMapper mapper = Mappers.getMapper(MenuMapper.class);
}
