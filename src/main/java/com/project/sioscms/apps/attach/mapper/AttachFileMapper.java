package com.project.sioscms.apps.attach.mapper;

import com.project.sioscms.apps.attach.domain.dto.AttachFileDto;
import com.project.sioscms.apps.attach.domain.entity.AttachFile;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttachFileMapper extends CommonEntityMapper<AttachFile, AttachFileDto.Request, AttachFileDto.Response> {
    AttachFileMapper mapper = Mappers.getMapper(AttachFileMapper.class);
}
