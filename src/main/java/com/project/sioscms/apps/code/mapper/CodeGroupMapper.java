package com.project.sioscms.apps.code.mapper;

import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CodeGroupMapper extends CommonEntityMapper<CodeGroup, CodeGroupDto.Request, CodeGroupDto.Response> {
    CodeGroupMapper mapper = Mappers.getMapper(CodeGroupMapper.class);
}
