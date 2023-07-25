package com.project.sioscms.apps.board.mapper;

import com.project.sioscms.apps.board.domain.dto.BoardDto;
import com.project.sioscms.apps.board.domain.entity.Board;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper extends CommonEntityMapper<Board, BoardDto.Request, BoardDto.Response> {
    BoardMapper mapper = Mappers.getMapper(BoardMapper.class);
}
