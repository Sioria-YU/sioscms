package com.project.sioscms.apps.contents.mapper;

import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.domain.entity.Contents;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContentsMapper extends CommonEntityMapper<Contents, ContentsDto.Request, ContentsDto.Response> {
    ContentsMapper mapper = Mappers.getMapper(ContentsMapper.class);
}
