package com.project.sioscms.cms.management.board.service;

import com.project.sioscms.apps.board.domain.repository.BoardMasterRepository;
import com.project.sioscms.apps.code.domain.dto.CodeDto;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.apps.code.domain.repository.CodeGroupRepository;
import com.project.sioscms.apps.code.domain.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardMasterManagementService {
    private final BoardMasterRepository boardMasterRepository;
    private final CodeGroupRepository codeGroupRepository;
    private final CodeRepository codeRepository;

    public List<CodeDto.Response> getBoardTypeCodeList(){
        CodeGroup codeGroup = codeGroupRepository.findByCodeGroupId("BOARD_TYPE_CD").orElse(null);

        if(codeGroup != null){
            List<Code> codeList = codeRepository.findAllByCodeGroup(codeGroup);
            return codeList.stream().map(Code::toResponse).collect(Collectors.toList());
        }else{
            return null;
        }
    }
}
