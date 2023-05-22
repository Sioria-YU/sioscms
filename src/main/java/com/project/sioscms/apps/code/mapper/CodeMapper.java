package com.project.sioscms.apps.code.mapper;

import com.project.sioscms.apps.code.domain.dto.CodeDto;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CodeMapper extends CommonEntityMapper<Code, CodeDto.Request, CodeDto.Response> {
    CodeMapper mapper = Mappers.getMapper(CodeMapper.class);
}
