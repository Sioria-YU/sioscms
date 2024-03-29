package com.project.sioscms.cms.management.system.service;

import com.project.sioscms.apps.code.domain.dto.CodeDto;
import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.apps.code.domain.repository.CodeGroupRepository;
import com.project.sioscms.apps.code.domain.repository.CodeRepository;
import com.project.sioscms.cms.management.system.domain.dto.CodeSearchDto;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeManagementService extends EgovAbstractServiceImpl {
    private final CodeGroupRepository codeGroupRepository;
    private final CodeRepository codeRepository;

    public SiosPage<CodeGroupDto.Response> getCodeGroupList(CodeSearchDto requestDto){
        ChangSolJpaRestriction rs = new ChangSolJpaRestriction();  //기본 값 AND 조건으로 적용됨.
        rs.equals("isDeleted", false);

        if(!ObjectUtils.isEmpty(requestDto.getCodeGroupId())){
            rs.like("codeGroupId", "%" + requestDto.getCodeGroupId() + "%");
        }
        if(!ObjectUtils.isEmpty(requestDto.getCodeGroupLabel())){
            rs.like("codeGroupLabel", "%" + requestDto.getCodeGroupLabel() + "%");
        }

        return new SiosPage<>(codeGroupRepository.findAll(rs.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC))
                .map(CodeGroup::toResponse)
                , requestDto.getPageSize());
    }

    public CodeGroupDto.Response getCodeGroup(String codeGroupId){
        if(codeGroupId == null || codeGroupId.isEmpty()){
            return null;
        }

        CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(codeGroupId).orElse(null);

        return codeGroup == null? null : codeGroup.toResponse();
    }

    public List<CodeDto.Response> getCodeList(String codeGroupId){
        ChangSolJpaRestriction rs = new ChangSolJpaRestriction();
        rs.equals("isDeleted", false);

        if(codeGroupId != null && !codeGroupId.isEmpty()){
            rs.equals("codeGroup.codeGroupId", codeGroupId);
        }

        return codeRepository.findAll(rs.toSpecification(), Sort.by(Sort.Direction.ASC, "orderNum"))
                .stream().map(Code::toResponse).collect(Collectors.toList());
    }
}
