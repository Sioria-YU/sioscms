package com.project.sioscms.apps.board.service;

import com.project.sioscms.apps.board.domain.dto.BoardMasterDto;
import com.project.sioscms.apps.board.domain.entity.BoardMaster;
import com.project.sioscms.apps.board.domain.repository.BoardMasterRepository;
import com.project.sioscms.apps.board.mapper.BoardMasterMapper;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.repository.CodeRepository;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardMasterService extends EgovAbstractServiceImpl {
    private final BoardMasterRepository boardMasterRepository;
    private final CodeRepository codeRepository;

    public SiosPage<BoardMasterDto.Response> getBoardMasterList(BoardMasterDto.Request requestDto){
        ChangSolJpaRestriction rs = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);
        rs.equals("isDeleted", false);

        if(!ObjectUtils.isEmpty(requestDto.getBoardName())){
            rs.like("boardName", "%" + requestDto.getBoardName() + "%");
        }

        if(!ObjectUtils.isEmpty(requestDto.getBoardTypeCode()) && !ObjectUtils.isEmpty(requestDto.getBoardTypeCode().getId())){
            rs.equals("boardTypeCode.id", requestDto.getBoardTypeCode().getId());
        }

        return new SiosPage<>(boardMasterRepository.findAll(rs.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC)).map(BoardMaster::toResponse)
                , requestDto.getPageSize());
    }

    public BoardMasterDto.Response getBoardMaster(Long id){
        return boardMasterRepository.findById(id).map(BoardMaster::toResponse).orElse(null);
    }

    @Transactional
    public BoardMasterDto.Response saveBoardMaster(BoardMasterDto.Request requestDto){
        BoardMaster entity = BoardMasterMapper.mapper.toEntity(requestDto);
        entity.setIsDeleted(false);

        //게시판 유형 주입
        Code code = codeRepository.findById(requestDto.getBoardTypeCode().getId()).orElse(null);
        entity.setBoardTypeCode(code);

        boardMasterRepository.save(entity);

        return entity.toResponse();
    }

    @Transactional
    public BoardMasterDto.Response updateBoardMaster(BoardMasterDto.Request requestDto){
        BoardMaster entity = boardMasterRepository.findById(requestDto.getId()).orElse(null);
        if(entity != null){
            Code code = codeRepository.findById(requestDto.getBoardTypeCode().getId()).orElse(null);

            entity.setBoardName(requestDto.getBoardName());
            entity.setBoardTypeCode(code);

            boardMasterRepository.save(entity);
            return entity.toResponse();
        }else{
            return null;
        }
    }

    @Transactional
    public void deleteBoardMaster(Long id){
        BoardMaster entity = boardMasterRepository.findById(id).orElse(null);
        if(entity != null){
            entity.setIsDeleted(true);
        }else{
            throw new NullPointerException("BoardMaster is null, request id : [" + id + "]");
        }
    }
}
