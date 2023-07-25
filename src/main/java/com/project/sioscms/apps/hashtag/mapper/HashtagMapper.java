package com.project.sioscms.apps.hashtag.mapper;

import com.project.sioscms.apps.hashtag.domain.dto.HashtagDto;
import com.project.sioscms.apps.hashtag.domain.entity.Hashtag;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.factory.Mappers;

public interface HashtagMapper extends CommonEntityMapper<Hashtag, HashtagDto.Request, HashtagDto.Response> {
    HashtagMapper mapper = Mappers.getMapper(HashtagMapper.class);
}
