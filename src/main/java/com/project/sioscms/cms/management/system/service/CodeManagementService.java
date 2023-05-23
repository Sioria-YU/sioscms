package com.project.sioscms.cms.management.system.service;

import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.apps.code.domain.repository.CodeGroupRepository;
import com.project.sioscms.apps.code.domain.repository.CodeRepository;
import com.project.sioscms.cms.management.system.domain.dto.CodeSearchDto;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeManagementService {
    private final CodeGroupRepository codeGroupRepository;
    private final CodeRepository codeRepository;

    public SiosPage<CodeGroupDto.Response> getCodeGroupList(CodeSearchDto requestDto){
        ChangSolJpaRestriction rs = new ChangSolJpaRestriction();  //기본 값 AND 조건으로 적용됨.
        rs.equals("isDeleted", false);

        if(requestDto.getCodeGroupId() != null && !requestDto.getCodeGroupId().isEmpty()){
            rs.like("codeGroupId", requestDto.getCodeGroupId());
        }
        if(requestDto.getCodeGroupLabel() != null && !requestDto.getCodeGroupLabel().isEmpty()){
            rs.like("codeGroupLabel", requestDto.getCodeGroupLabel());
        }

        return new SiosPage<>(codeGroupRepository.findAll(rs.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC))
                .map(CodeGroup::toResponse)
                , requestDto.getPageSize());
    }
}
