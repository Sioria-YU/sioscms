package com.project.sioscms.apps.log.mapper;

import com.project.sioscms.apps.log.domain.dto.ExceptionLogDto;
import com.project.sioscms.apps.log.domain.entity.ExceptionLog;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExceptionLogMapper extends CommonEntityMapper<ExceptionLog, ExceptionLogDto.Request, ExceptionLogDto.Response> {
    ExceptionLogMapper mapper = Mappers.getMapper(ExceptionLogMapper.class);
}
