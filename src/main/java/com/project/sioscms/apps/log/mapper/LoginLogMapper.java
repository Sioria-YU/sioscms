package com.project.sioscms.apps.log.mapper;

import com.project.sioscms.apps.log.domain.dto.LoginLogDto;
import com.project.sioscms.apps.log.domain.entity.LoginLog;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginLogMapper extends CommonEntityMapper<LoginLog, LoginLogDto.Request, LoginLogDto.Response> {
    LoginLogMapper mapper = Mappers.getMapper(LoginLogMapper.class);
}
