package com.project.sioscms.apps.attach.mapper;

import com.project.sioscms.apps.attach.domain.dto.AttachFileGroupDto;
import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttachFileGroupMapper extends CommonEntityMapper<AttachFileGroup, AttachFileGroupDto.Request, AttachFileGroupDto.Response> {
    AttachFileGroupMapper mapper = Mappers.getMapper(AttachFileGroupMapper.class);
}
