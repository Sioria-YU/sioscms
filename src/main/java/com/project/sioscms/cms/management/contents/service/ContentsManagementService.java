package com.project.sioscms.cms.management.contents.service;

import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.attach.domain.repository.AttachFileGroupRepository;
import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.domain.entity.Contents;
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

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsManagementService {

    private final ContentsRepository contentsRepository;
    private final AttachFileGroupRepository attachFileGroupRepository;

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
    public ContentsDto.Response saveContents(ContentsDto.Request requestDto){
        Contents contents = ContentsMapper.mapper.toEntity(requestDto);
        contents.setIsDeleted(false);

        if(requestDto.getAttachFileGroupId() != null){
            AttachFileGroup attachFileGroup = attachFileGroupRepository.findById(requestDto.getAttachFileGroupId()).orElse(null);

            if(attachFileGroup != null){
                contents.setAttachFileGroup(attachFileGroup);
            }
        }

        contentsRepository.save(contents);
        return contents.toResponse();
    }
}
