package com.project.sioscms.apps.contents.service;

import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.domain.entity.Contents;
import com.project.sioscms.apps.contents.domain.repository.ContentsRepository;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService extends EgovAbstractServiceImpl {

    private final ContentsRepository contentsRepository;

    public List<ContentsDto.Response> getContentsList(ContentsDto.Request requestDto){

        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);

        if(!ObjectUtils.isEmpty(requestDto.getTitle())){
            restriction.like("title", "%" + requestDto.getTitle() + "%");
        }

        return contentsRepository.findAll(restriction.toSpecification(), Sort.by(Sort.Direction.DESC, "createdDateTime"))
                .stream().map(Contents::toResponse).collect(Collectors.toList());
    }
}
