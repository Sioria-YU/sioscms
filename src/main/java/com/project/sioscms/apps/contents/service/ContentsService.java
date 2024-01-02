package com.project.sioscms.apps.contents.service;

import com.project.sioscms.apps.attach.domain.dto.AttachFileDto;
import com.project.sioscms.apps.attach.domain.dto.AttachFileGroupDto;
import com.project.sioscms.apps.attach.domain.entity.AttachFile;
import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.attach.domain.repository.AttachFileGroupRepository;
import com.project.sioscms.apps.attach.domain.repository.AttachFileRepository;
import com.project.sioscms.apps.attach.service.AttachFileService;
import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.domain.entity.Contents;
import com.project.sioscms.apps.contents.domain.entity.ContentsHistory;
import com.project.sioscms.apps.contents.domain.repository.ContentsHistoryRepository;
import com.project.sioscms.apps.contents.domain.repository.ContentsRepository;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService extends EgovAbstractServiceImpl {

    @Value("${contents.path}")
    private String CONTENTS_PATH;

    private final AttachFileService attachFileService;
    private final ContentsRepository contentsRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final AttachFileGroupRepository attachFileGroupRepository;
    private final AttachFileRepository attachFileRepository;


    public List<ContentsDto.Response> getContentsList(ContentsDto.Request requestDto){

        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);

        if(!ObjectUtils.isEmpty(requestDto.getTitle())){
            restriction.like("title", "%" + requestDto.getTitle() + "%");
        }

        return contentsRepository.findAll(restriction.toSpecification(), Sort.by(Sort.Direction.DESC, "createdDateTime"))
                .stream().map(Contents::toResponse).collect(Collectors.toList());
    }

    public Boolean duplicationCheckContentsName(final String contentsName){
        return contentsRepository.countByContentsName(contentsName) == 0;
    }

    @Transactional
    public List<AttachFileDto.Response> saveAttachFiles(ContentsDto.Request requestDto, List<MultipartFile> files){
        if(ObjectUtils.isEmpty(requestDto.getId())){
            return null;
        }

        Contents contents = contentsRepository.findById(requestDto.getId()).orElse(null);

        if(contents != null) {
            AttachFileGroupDto.Response attachFileGroupResponse = attachFileService.multiUpload(files, requestDto.getAttachFileGroupId(), "contents");
            AttachFileGroup attachFileGroup = attachFileGroupRepository.findById(attachFileGroupResponse.getId()).orElse(null);
            if(attachFileGroup == null){
                return null;
            }
            //첨부파일을 처음 등록할 경우
            if(ObjectUtils.isEmpty(contents.getAttachFileGroup())){
                contents.setAttachFileGroup(attachFileGroup);
            }

            List<AttachFile> attachFileList = attachFileRepository.findAllByAttachFileGroupAndIsDeletedOrderByFileOrderNumAsc(attachFileGroup, false);
            if(!ObjectUtils.isEmpty(attachFileList)){
                List<AttachFileDto.Response> resultList = attachFileList.stream().map(AttachFile::toResponse).toList();
                resultList.forEach(v -> v.setAttachFileGroupId(attachFileGroup.getId()));
                return resultList;
            }
        }

        return null;
    }

    @Transactional
    public void deleteContents(final Long id){
        contentsRepository.findById(id).ifPresent(contents -> contents.setIsDeleted(true));
    }

    @Transactional
    public Boolean changeVersion(final Long id, final Long historyId){
        if(!ObjectUtils.isEmpty(id) && !ObjectUtils.isEmpty(historyId)){
            Contents contents = contentsRepository.findById(id).orElse(null);

            if(contents != null){
                //현재 사용중인 히스토리 미사용 처리
                contentsHistoryRepository.findFirstByContentsAndIsUsedAndIsDeleted(contents, true, false).ifPresent(contentsHistory -> contentsHistory.setIsUsed(false));
                ContentsHistory contentsHistory = contentsHistoryRepository.findById(historyId).orElse(null);
                if(contentsHistory != null){
                    contentsHistory.setIsUsed(true);

                    contents.setContent(contentsHistory.getContent());
                    contentsRepository.flush();
                    contentsHistoryRepository.flush();

                    //파일 생성 로직
                    contentsFileSave(contents.getContentsName(), contents.getContent());
                    return true;
                }
            }
        }
        return false;
    }

    public Map<String,String> getCompareHistory(final Long historyId){
        if(!ObjectUtils.isEmpty(historyId)) {
            ContentsHistory contentsHistory = contentsHistoryRepository.findById(historyId).orElse(null);
            if (contentsHistory != null) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("selectedVersionContent", contentsHistory.getContent()); //선택 버전
                contentsHistoryRepository.findFirstByContentsAndIsUsedAndIsDeleted(contentsHistory.getContents(), true, false)
                        .ifPresent(nowContentsHistory -> resultMap.put("nowVersionContent", nowContentsHistory.getContent())); //현재 버전

                return resultMap;
            }
        }
        return null;
    }

    @Synchronized
    public void contentsFileSave(final String fileName, final String content){
        final String fileFullPath = CONTENTS_PATH + File.separator + fileName + ".html";

        File file = new File(fileFullPath);

        if(file.exists()){
            //기존 파일이 있다면 삭제
            if(!file.delete()){
                log.error(fileName + " unable to delete");
            }
        }

        try {
            //파일 생성
            if(!file.createNewFile()){
                log.error(fileName + " created failed");
            }else{
                //파일 내용 쓰기
                FileWriter fw = new FileWriter(file);
                BufferedWriter writer = new BufferedWriter(fw);

                writer.write(content);
                writer.close();
            }
        } catch (IOException e) {
            log.error(e.toString());
        }

    }
}
