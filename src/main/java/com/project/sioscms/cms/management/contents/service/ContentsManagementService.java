package com.project.sioscms.cms.management.contents.service;

import com.project.sioscms.apps.attach.domain.dto.AttachFileGroupDto;
import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.attach.domain.repository.AttachFileGroupRepository;
import com.project.sioscms.apps.attach.service.AttachFileService;
import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.domain.entity.Contents;
import com.project.sioscms.apps.contents.domain.entity.ContentsHistory;
import com.project.sioscms.apps.contents.domain.repository.ContentsHistoryRepository;
import com.project.sioscms.apps.contents.domain.repository.ContentsRepository;
import com.project.sioscms.apps.contents.mapper.ContentsMapper;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsManagementService {

    private final ContentsRepository contentsRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final AttachFileGroupRepository attachFileGroupRepository;

    private final AttachFileService attachFileService;

    public SiosPage<ContentsDto.Response> getContentsList(ContentsDto.Request requestDto) {
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);

        if (!ObjectUtils.isEmpty(requestDto.getTitle())) {
            restriction.like("title", "%" + requestDto.getTitle() + "%");
        }
        if(!ObjectUtils.isEmpty(requestDto.getStartDate())){
            restriction.greaterThanEquals("createdDateTime", LocalDateTime.of(requestDto.getStartDate(), LocalTime.of(0, 0, 0)));
        }

        if(!ObjectUtils.isEmpty(requestDto.getEndDate())){
            restriction.lessThanEquals("createdDateTime", LocalDateTime.of(requestDto.getStartDate(), LocalTime.of(23, 59, 59)));
        }

        return new SiosPage<>(contentsRepository.findAll(restriction.toSpecification(), requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC))
                .map(Contents::toResponse)
                , requestDto.getPageSize());
    }

    public ContentsDto.Response getContents(Long id){
        Contents contents = contentsRepository.findById(id).orElse(null);
        return contents != null? contents.toResponse() : null;
    }

    @Transactional
    public ContentsDto.Response saveContents(ContentsDto.Request requestDto, List<MultipartFile> files){
        if(files != null && !files.isEmpty()) {
            AttachFileGroupDto.Response attachFileGroupDto = attachFileService.multiUpload(files, null);

            if (attachFileGroupDto != null) {
                requestDto.setAttachFileGroupId(attachFileGroupDto.getId());
            }
        }

        Contents contents = ContentsMapper.mapper.toEntity(requestDto);
        contents.setIsDeleted(false);

        if(requestDto.getAttachFileGroupId() != null){
            attachFileGroupRepository.findById(requestDto.getAttachFileGroupId()).ifPresent(contents::setAttachFileGroup);
        }

        contentsRepository.save(contents);

        //히스토리 생성
        ContentsHistory contentsHistory = new ContentsHistory(contents, contents.getContent(), 1L, true, false);
        contentsHistoryRepository.save(contentsHistory);

        return contents.toResponse();
    }

    @Transactional
    public Boolean saveNewVersion(ContentsDto.Request requestDto){
        if(!ObjectUtils.isEmpty(requestDto.getId())){
            Contents contents = contentsRepository.findById(requestDto.getId()).orElse(null);
            if(contents != null){
                contents.setContent(requestDto.getContent());
                long lastVersion = 1L;

                //마지막 버전을 구한다.
                ContentsHistory topHistory = contentsHistoryRepository.findTopByContentsAndIsDeletedOrderByVersionDesc(contents, false).orElse(null);
                if(topHistory != null){
                    lastVersion = topHistory.getVersion() + 1;
                }

                //현재 사용중인 history를 미사용으로 변경
                contentsHistoryRepository.findFirstByContentsAndIsUsedAndIsDeleted(contents, true, false).ifPresent(usedHistory -> usedHistory.setIsUsed(false));

                ContentsHistory contentsHistory = new ContentsHistory(contents, contents.getContent(), lastVersion, true, false);
                contentsHistoryRepository.save(contentsHistory);

                return true;
            }
        }

        return false;
    }

    public void preView(HttpServletResponse response, String content){
        if (response == null) {
            throw new NullPointerException();
        }
        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println(content);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public void preViewHistory(HttpServletResponse response, Long historyId){
        if (response == null || ObjectUtils.isEmpty(historyId)) {
            throw new NullPointerException();
        }else {

            ContentsHistory contentsHistory = contentsHistoryRepository.findById(historyId).orElse(null);

            if (contentsHistory == null) {
                throw new NullPointerException();
            }else {
                response.setContentType("text/html; charset=UTF-8");

                try (PrintWriter out = response.getWriter()) {
                    out.println(contentsHistory.getContent());
                } catch (IOException e) {
                    log.error(e.toString());
                }
            }
        }
    }
}
