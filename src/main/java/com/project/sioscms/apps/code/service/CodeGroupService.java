package com.project.sioscms.apps.code.service;

import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.apps.code.domain.repository.CodeGroupRepository;
import com.project.sioscms.apps.code.mapper.CodeGroupMapper;
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

    @Transactional
    public CodeGroupDto.Response saveCodeGroup(CodeGroupDto.Request dto){
        if(dto != null && dto.getCodeGroupId() != null && dto.getCodeGroupLabel() != null){
            CodeGroup entity = CodeGroupMapper.mapper.toEntity(dto);
            codeGroupRepository.save(entity);

            return entity.toResponse();
        }else{
            return null;
        }
    }

    @Transactional
    public CodeGroupDto.Response updateCodeGroup(CodeGroupDto.Request dto){
        if(dto != null && dto.getCodeGroupId() != null && dto.getCodeGroupLabel() != null){
            CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(dto.getCodeGroupId()).orElseThrow(NullPointerException::new);
            codeGroup.setCodeGroupLabel(dto.getCodeGroupLabel());
            codeGroup.setCodeGroupNote(dto.getCodeGroupNote());
            codeGroup.setIsUsed(dto.getIsUsed());

            return codeGroup.toResponse();
        }else{
            return null;
        }
    }

    @Transactional
    public Boolean deleteCodeGroup(String codeGroupId){
        if(codeGroupId != null && !codeGroupId.isEmpty()){
            CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(codeGroupId).orElseThrow(NullPointerException::new);
            codeGroup.setIsDeleted(true);
            codeGroup.setIsUsed(false);

            return true;
        }else{
            return false;
        }
    }
}
