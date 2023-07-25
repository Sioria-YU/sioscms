package com.project.sioscms.apps.board.mapper;

import com.project.sioscms.apps.board.domain.dto.BoardMasterDto;
import com.project.sioscms.apps.board.domain.entity.BoardMaster;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMasterMapper extends CommonEntityMapper<BoardMaster, BoardMasterDto.Request, BoardMasterDto.Response> {
    BoardMasterMapper mapper = Mappers.getMapper(BoardMasterMapper.class);
}
