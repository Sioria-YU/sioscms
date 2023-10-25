package com.project.sioscms.apps.contents.service;

import com.project.sioscms.apps.contents.domain.dto.ContentsHistoryDto;
import com.project.sioscms.apps.contents.domain.entity.ContentsHistory;
import com.project.sioscms.apps.contents.domain.repository.ContentsHistoryRepository;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContentsHistoryService {
    private final ContentsHistoryRepository contentsHistoryRepository;

    public List<ContentsHistoryDto.Response> getContentsHistoryList(Long contentsId) {
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);
        restriction.equals("contents.id", contentsId);
        restriction.equals("isDeleted", false);

        return contentsHistoryRepository.findAll(restriction.toSpecification()
                , Sort.by(Sort.Direction.DESC, "version"))
                .stream().map(ContentsHistory::toResponse)
                .collect(Collectors.toList());
    }

}
