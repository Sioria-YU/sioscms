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

        return new SiosPage<>(contentsRepository.findAll(restriction.toSpecification(), requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC))
                .map(Contents::toResponse)
                , requestDto.getPageSize());
    }
}
