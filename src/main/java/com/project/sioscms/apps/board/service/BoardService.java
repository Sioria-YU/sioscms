package com.project.sioscms.apps.board.service;

import com.project.sioscms.apps.attach.domain.repository.AttachFileGroupRepository;
import com.project.sioscms.apps.board.domain.dto.BoardDto;
import com.project.sioscms.apps.board.domain.entity.Board;
import com.project.sioscms.apps.board.domain.entity.BoardMaster;
import com.project.sioscms.apps.board.domain.repository.BoardMasterRepository;
import com.project.sioscms.apps.board.domain.repository.BoardRepository;
import com.project.sioscms.apps.board.mapper.BoardMapper;
import com.project.sioscms.apps.hashtag.domain.entity.BoardHashtag;
import com.project.sioscms.apps.hashtag.domain.entity.Hashtag;
import com.project.sioscms.apps.hashtag.domain.repository.BoardHashtagRepository;
import com.project.sioscms.apps.hashtag.domain.repository.HashtagRepository;
import com.project.sioscms.common.utils.common.parser.HtmlParseUtil;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMasterRepository boardMasterRepository;
    private final HashtagRepository hashtagRepository;
    private final BoardHashtagRepository boardHashtagRepository;
    private final AttachFileGroupRepository attachFileGroupRepository;

    public SiosPage<BoardDto.Response> getBoardList(BoardDto.Request requestDto){
        ChangSolJpaRestriction rs = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);
        rs.equals("isDeleted", false);

        if(requestDto.getBoardMasterId() != null){
            rs.equals("boardMaster.id", requestDto.getBoardMasterId());
        }

        if(requestDto.getTitle() != null){
            rs.like("title", "%" + requestDto.getTitle() + "%");
        }

        if(requestDto.getKeyword() != null){
            rs.like("contentWithoutHtml", "%" + requestDto.getKeyword().replace(" ", "") + "%");
        }

        return new SiosPage<>(boardRepository.findAll(rs.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC)).map(Board::toResponse)
                , requestDto.getPageSize());
    }

    public BoardDto.Response getBoard(Long id){
        Board board = boardRepository.findById(id).orElse(null);
        if(board != null){
            return board.toResponse();
        }else{
            return null;
        }
    }

    @Transactional
    public BoardDto.Response saveBoard(BoardDto.Request requestDto){
        Board board = BoardMapper.mapper.toEntity(requestDto);
        BoardMaster boardMaster = boardMasterRepository.findById(requestDto.getBoardMasterId()).orElse(null);

        if(boardMaster == null){
            return null;
        }

        board.setBoardMaster(boardMaster);
        board.setIsDeleted(false);
        //content에서 html태그 및 공백을 제거하고 입력
        board.setContentWithoutHtml(HtmlParseUtil.escapeHtmlTag(board.getContent()));

        //첨부파일 등록 로직
        if(requestDto.getAttachFileGroupId() != null){
            attachFileGroupRepository.findById(requestDto.getAttachFileGroupId()).ifPresent(board::setAttachFileGroup);
        }

        //해시태그 등록 로직
        if(requestDto.getHashtagList() != null && requestDto.getHashtagList().size() > 0){
            for (String tag : requestDto.getHashtagList()) {
                Hashtag hashtag = hashtagRepository.findByHashtagNameAndIsDeleted(tag, false).orElse(null);
                //해시 태그가 없을 경우 새로 생성
                if(hashtag == null){
                    hashtag = new Hashtag();
                    hashtag.setHashtagName(tag);
                    hashtag.setIsDeleted(false);
                    hashtag.setIsDisplay(true);
                    hashtagRepository.save(hashtag);
                    hashtagRepository.flush();
                }

                //게시판 해시태그 매핑 테이블 저장
                BoardHashtag boardHashtag = new BoardHashtag();
                boardHashtag.setBoard(board);
                boardHashtag.setHashtag(hashtag);

                boardHashtagRepository.save(boardHashtag);
            }
        }

        boardRepository.save(board);
        return board.toResponse();
    }

    @Transactional
    public BoardDto.Response updateBoard(BoardDto.Request requestDto){
        Board board = boardRepository.findById(requestDto.getId()).orElse(null);

        if(board == null){
            return null;
        }

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setContentWithoutHtml(HtmlParseUtil.escapeHtmlTag(requestDto.getContent()));
        board.setOption1(requestDto.getOption1());
        board.setOption2(requestDto.getOption2());
        board.setOption3(requestDto.getOption3());
        board.setOption4(requestDto.getOption4());
        board.setOption5(requestDto.getOption5());

        //해시태그 등록 로직
        //기존 해시태그 삭제
        boardHashtagRepository.deleteAllByBoard(board);
        if(requestDto.getHashtagList() != null && requestDto.getHashtagList().size() > 0){
            for (String tag : requestDto.getHashtagList()) {
                if(tag.replace(" ", "").length() == 0){
                    continue;
                }
                tag = tag.replace(" ", "");

                Hashtag hashtag = hashtagRepository.findByHashtagNameAndIsDeleted(tag, false).orElse(null);
                //해시 태그가 없을 경우 새로 생성
                if(hashtag == null){
                    hashtag = new Hashtag();
                    hashtag.setHashtagName(tag);
                    hashtag.setIsDeleted(false);
                    hashtag.setIsDisplay(true);
                    hashtagRepository.save(hashtag);
                    hashtagRepository.flush();
                }

                //게시판 해시태그 매핑 테이블 저장
                BoardHashtag boardHashtag = new BoardHashtag();
                boardHashtag.setBoard(board);
                boardHashtag.setHashtag(hashtag);

                boardHashtagRepository.save(boardHashtag);
            }
        }

        boardRepository.flush();
        return board.toResponse();
    }

    public boolean updateBoardViewCount(Long id){
        Board board = boardRepository.findById(id).orElse(null);

        if(board == null){
            return false;
        }else{
            board.setViewCount(board.getViewCount() + 1);
            boardRepository.flush();
            return true;
        }
    }

    @Transactional
    public boolean deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElse(null);
        if(board == null){
            return false;
        }else{
            board.setIsDeleted(true);
            boardRepository.flush();
            return true;
        }
    }
}
