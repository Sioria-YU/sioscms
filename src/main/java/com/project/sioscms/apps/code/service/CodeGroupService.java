package com.project.sioscms.apps.code.service;

import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.apps.code.domain.repository.CodeGroupRepository;
import com.project.sioscms.apps.code.domain.repository.CodeRepository;
import com.project.sioscms.apps.code.mapper.CodeGroupMapper;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeGroupService extends EgovAbstractServiceImpl {
    private final CodeGroupRepository codeGroupRepository;
    private final CodeRepository codeRepository;

    /**
     * 코드그룹 목록 조회
     * @param dto CodeGroupDto.Request
     * @return List<CodeGroupDto.Response>
     */
    public List<CodeGroupDto.Response> getCodeGroupList(CodeGroupDto.Request dto){

        ChangSolJpaRestriction rs = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);
        rs.equals("isDeleted", false);

        if(dto.getCodeGroupId() != null){
            rs.iLike("codeGroupId", "%" + dto.getCodeGroupId() + "%");
        }

        if(dto.getCodeGroupLabel() != null){
            rs.like("codeGroupLabel", "%" + dto.getCodeGroupLabel() + "%");
        }

        return codeGroupRepository.findAll(rs.toSpecification(), Sort.by(Sort.Direction.DESC, "createdDateTime"))
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
            entity.setIsDeleted(false);

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

    public Boolean duplicationCheck(String codeGroupId){
        if(codeGroupId != null && !codeGroupId.isEmpty()){
            CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(codeGroupId).orElse(null);
            return codeGroup == null;
        }else{
            return false;
        }
    }

    @Transactional
    public Boolean multipleDelete(String[] codeGroupIdList){
        if(codeGroupIdList != null && codeGroupIdList.length > 0){
            for (String codeGroupId : codeGroupIdList) {
                CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(codeGroupId).orElse(null);
                if(codeGroup != null){
                    //코드 그룹 하위 자식들을 먼저 삭제한다.
                    List<Code> codeList = codeRepository.findAllByCodeGroup(codeGroup);
                    if(codeList != null){
                        codeRepository.deleteAll(codeList);
                    }

                    //코드 그룹 삭제
                    codeGroupRepository.delete(codeGroup);
                }
            }

            codeGroupRepository.flush();
            return true;
        }else{
            return false;
        }
    }
}
