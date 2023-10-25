package com.project.sioscms.apps.contents.mapper;

import com.project.sioscms.apps.contents.domain.dto.ContentsHistoryDto;
import com.project.sioscms.apps.contents.domain.entity.ContentsHistory;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContentsHistoryMapper extends CommonEntityMapper<ContentsHistory, ContentsHistoryDto.Request, ContentsHistoryDto.Response> {
    ContentsHistoryMapper mapper = Mappers.getMapper(ContentsHistoryMapper.class);
}
