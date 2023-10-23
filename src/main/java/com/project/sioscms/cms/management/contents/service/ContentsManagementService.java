package com.project.sioscms.cms.management.contents.service;

import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.domain.entity.Contents;
import com.project.sioscms.apps.contents.domain.repository.ContentsRepository;
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
}
