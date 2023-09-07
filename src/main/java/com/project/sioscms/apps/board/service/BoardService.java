package com.project.sioscms.apps.board.service;

import com.google.common.collect.Lists;
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
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService extends EgovAbstractServiceImpl {
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

        if(requestDto.getTitle() != null && !requestDto.getTitle().isEmpty()){
            rs.like("title", "%" + requestDto.getTitle() + "%");
        }

        if(requestDto.getKeyword() != null && !requestDto.getKeyword().isEmpty()){
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
        BoardMaster boardMaster = boardMasterRepository.findById(requestDto.getBoardMasterId()).orElse(null);

        if(boardMaster == null){
            return null;
        }

        Board board = BoardMapper.mapper.toEntity(requestDto);
        board.setBoardMaster(boardMaster);
        board.setIsDeleted(false);
        board.setViewCount(0L);
        //content에서 html태그 및 공백을 제거하고 입력
        board.setContentWithoutHtml(HtmlParseUtil.escapeHtmlTag(board.getContent()).replaceAll(" ",""));

        //첨부파일 등록 로직
        if(requestDto.getAttachFileGroupId() != null){
            attachFileGroupRepository.findById(requestDto.getAttachFileGroupId()).ifPresent(board::setAttachFileGroup);
        }

        //해시태그 등록 로직
        if(requestDto.getHashtagList() != null && requestDto.getHashtagList().size() > 0){
            for (String tag : requestDto.getHashtagList()) {
                saveBoardHashTag(board, tag);
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
        board.setContentWithoutHtml(HtmlParseUtil.escapeHtmlTag(requestDto.getContent()).replaceAll(" ",""));
        board.setOption1(requestDto.getOption1());
        board.setOption2(requestDto.getOption2());
        board.setOption3(requestDto.getOption3());
        board.setOption4(requestDto.getOption4());
        board.setOption5(requestDto.getOption5());

        //해시태그 등록 로직

        //기존 해시태그가 있었고, 수정하여 모두 삭제했을 경우
        hashtagUpdate(board, requestDto);

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


    /**
     * 게시판 해시태그 업데이트 로직
     * @param board
     * @param requestDto
     * @implNote
     * - 입력한 해시태그가 있을 경우
     *         1.기존 등록한 해시태그가 있는 경우
     *             1.1.기존 해시태그 리스트를 불러옴
     *             1.2.입력한 해시태그와 비교하여 추가,삭제 목록 추출
     *             1.3.삭제 목록 삭제처리
     *             1.4.추가 목록이 해시태그로 등록되어 있는지 확인
     *                 1.4.1.추가 목록이 해시태그로 등록되어 있는 경우
     *                     1.4.1.1.게시판 해시태그 등록
     *                 1.4.2.추가 목록이 해시태그로 등록되지 않은 경우
     *                     1.4.2.1.해시태그 등록
     *                     1.4.2.2.게시판 해시태그 등록
     *         2.기존 등록한 해시태그가 없는 경우
     *             2.1.추가 목록이 해시태그로 등록되어 있는지 확인
     *                 2.1.1.추가 목록이 해시태그로 등록되어 있는 경우
     *                     2.1.1.1.게시판 해시태그 등록
     *                 2.1.2.추가 목록이 해시태그로 등록되지 않은 경우
     *                     2.1.2.1.해시태그 등록
     *                     2.1.2.2.게시판 해시태그 등록
     */
    @Transactional
    protected void hashtagUpdate(Board board, BoardDto.Request requestDto){
        if(board.getBoardHashtagSet() != null && !board.getBoardHashtagSet().isEmpty() && (requestDto.getHashtagList() == null || requestDto.getHashtagList().isEmpty())) {
            boardHashtagRepository.deleteAllByBoard(board);
            board.setBoardHashtagSet(null);
        }//입력한 해시태그가 있을 경우
        else if(requestDto.getHashtagList() != null && requestDto.getHashtagList().size() > 0){
            //기존 해시태그가 있을 때
            if(board.getBoardHashtagSet() != null && !board.getBoardHashtagSet().isEmpty()){
                List<BoardHashtag> removeTagList = Lists.newArrayList();
                List<String> appendTagList = Lists.newArrayList();

                removeTagList = board.getBoardHashtagSet()
                        .stream()
                        .filter(boardHashtag -> requestDto.getHashtagList().stream().noneMatch(tag -> boardHashtag.getHashtag().getHashtagName().equals(tag)))
                        .collect(Collectors.toList());

                appendTagList = requestDto.getHashtagList()
                        .stream()
                        .filter(tag -> board.getBoardHashtagSet().stream().noneMatch(boardHashtag -> boardHashtag.getHashtag().getHashtagName().equals(tag)))
                        .collect(Collectors.toList());

                //삭제태그
                if(!removeTagList.isEmpty()){
                    boardHashtagRepository.deleteAll(removeTagList);
                }

                if(!appendTagList.isEmpty()){
                    for (String tag : appendTagList) {
                        tag = tag.replaceAll(" ", "");

                        if(tag.length() == 0){
                            continue;
                        }

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

            }//기존 해시 태그가 없을 때
            else{
                for (String tag : requestDto.getHashtagList()) {
                    saveBoardHashTag(board, tag);
                }
            }
        }
    }

    @Transactional
    protected void saveBoardHashTag(Board board, String tag){
        if(board == null || tag == null || tag.isEmpty()){
            return;
        }else{
            tag = tag.replaceAll(" ", "");
        }

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
