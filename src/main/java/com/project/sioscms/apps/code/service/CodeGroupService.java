package com.project.sioscms.apps.code.service;

import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.apps.code.domain.repository.CodeGroupRepository;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeGroupService {
    private final CodeGroupRepository codeGroupRepository;

    /**
     * 코드그룹 목록 조회
     * @param dto CodeGroupDto.Request
     * @return List<CodeGroupDto.Response>
     */
    public List<CodeGroupDto.Response> getCodeGroupList(CodeGroupDto.Request dto){

        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);
        restriction.equals("isDeleted", false);

        return codeGroupRepository.findAll(restriction.toSpecification(), Sort.by(Sort.Direction.DESC, "createdDateTime"))
                .stream().map(CodeGroup::toResponse).collect(Collectors.toList());
    }

    /**
     * 코드그룹 단일 조회
     * @param codeGroupId String
     * @return CodeGroupDto.Response
     */
    public CodeGroupDto.Response getCodeGroup(String codeGroupId){
        if(codeGroupId != null){
            CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(codeGroupId).orElse(null);
            return codeGroup != null? codeGroup.toResponse() : null;
        }else{
            return null;
        }
    }
}
