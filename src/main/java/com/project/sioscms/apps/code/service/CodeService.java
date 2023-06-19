package com.project.sioscms.apps.code.service;

import com.project.sioscms.apps.code.domain.dto.CodeDto;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.apps.code.domain.repository.CodeGroupRepository;
import com.project.sioscms.apps.code.domain.repository.CodeRepository;
import com.project.sioscms.apps.code.mapper.CodeMapper;
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
public class CodeService {
    private final CodeRepository codeRepository;
    private final CodeGroupRepository codeGroupRepository;

    /**
     * 코드 목록 조회
     * @param dto CodeDto.Request
     * @return List<CodeDto.Response>
     */
    public List<CodeDto.Response> getCodeList(CodeDto.Request dto){
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);
        restriction.equals("isDeleted", false);

        if(dto.getCodeGroupId() != null){
            CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(dto.getCodeGroupId()).orElse(null);
            if(codeGroup != null) {
                restriction.equals("codeGroup", codeGroup.getCodeGroupId());
            }
        }

        return codeRepository.findAll(restriction.toSpecification(), Sort.by(Sort.Direction.ASC, "orderNum"))
                .stream().map(Code::toResponse).collect(Collectors.toList());
    }

    /**
     * 코드 조회
     * @param codeId String
     * @return CodeDto.Response
     */
    public CodeDto.Response getCode(String codeId){
        /*if(codeId != null) {
            Code code = codeRepository.findByCodeId(codeId).orElse(null);
            return code != null ? code.toResponse() : null;
        }else{
            return null;
        }*/
        return null;
    }

    /**
     * 코드 등록
     * @param dto CodeDto.Request
     * @return CodeDto.Response
     */
    @Transactional
    public CodeDto.Response saveCode(CodeDto.Request dto){
        if(dto != null && dto.getCodeId() != null && dto.getCodeGroupId() != null && dto.getCodeLabel() != null){
            Code entity = CodeMapper.mapper.toEntity(dto);
            entity.setIsDeleted(false);
            entity.setOrderNum(codeRepository.countByCodePk_CodeGroup_CodeGroupIdAndIsDeleted(dto.getCodeGroupId(), false).intValue());

            CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(dto.getCodeGroupId()).orElse(null);
            if(codeGroup != null){
                Code.CodePk pk = new Code.CodePk();
                pk.setCodeGroup(codeGroup);
                pk.setCodeId(dto.getCodeId());
                entity.setCodePk(pk);
            }else{
                return null;
            }

            codeRepository.save(entity);

            return entity.toResponse();
        }else{
            return null;
        }
    }

    @Transactional
    public CodeDto.Response updateCode(CodeDto.Request dto){
        if(dto != null && dto.getCodeId() != null && dto.getCodeGroupId() != null && dto.getCodeLabel() != null){
            Code entity = codeRepository.findByCodePk_CodeGroup_CodeGroupIdAndCodePk_CodeId(dto.getCodeGroupId(), dto.getCodeId()).orElse(null);

            if(entity != null){
                entity.setCodeLabel(dto.getCodeLabel());
                entity.setOption1(dto.getOption1());
                entity.setOption2(dto.getOption2());
                entity.setOption3(dto.getOption3());
                entity.setOption4(dto.getOption4());
                entity.setIsUsed(dto.getIsUsed());

                return entity.toResponse();
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 코드 다중삭제
     * @param codeIdList String[]
     * @return Boolean
     */
    @Transactional
    public Boolean multipleDeleteCode(String codeGroupId, String[] codeIdList){
        if(codeGroupId != null && codeIdList != null && codeIdList.length > 0){
            for (String cd : codeIdList) {
                Code code = codeRepository.findByCodePk_CodeGroup_CodeGroupIdAndCodePk_CodeId(codeGroupId, cd).orElse(null);
                if (code != null) {
                    codeRepository.delete(code);
                } else {
                    return false;
                }
            }
            codeRepository.flush();
            return true;
        }else{
            return false;
        }
    }

    /**
     * 코드 아이디 중복 체크
     * @param codeId String
     * @return Boolean
     */
    public Boolean duplicationCheck(String codeGroupId, String codeId){
        if(codeGroupId != null && codeId != null && !codeId.isEmpty()){
            Code code = codeRepository.findByCodePk_CodeGroup_CodeGroupIdAndCodePk_CodeId(codeGroupId, codeId).orElse(null);
            return code == null;
        }else{
            return false;
        }
    }

    @Transactional
    public Boolean orderSwapUpdate(String codeGroupId, String codeId1, String codeId2){
        if(codeGroupId != null && codeId1 != null && codeId2 != null){
            CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId(codeGroupId).orElse(null);

            if(codeGroup != null){
                Code code1 = codeRepository.findByCodePk_CodeGroupAndCodePk_CodeId(codeGroup, codeId1).orElse(null);
                Code code2 = codeRepository.findByCodePk_CodeGroupAndCodePk_CodeId(codeGroup, codeId2).orElse(null);

                //정렬 순번 스왑
                if(code1 != null && code2 != null){
                    int tmpOrderNum = code1.getOrderNum();
                    code1.setOrderNum(code2.getOrderNum());
                    code2.setOrderNum(tmpOrderNum);
                    codeRepository.flush();

                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
