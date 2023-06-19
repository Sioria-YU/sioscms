package com.project.sioscms.apps.code.domain.repository;

import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends CommonJpaRepository<Code, Long> {
    Optional<Code> findByCodeId(String codeId);

    Optional<Code> findByCodeGroupAndCodeId(CodeGroup codeGroup, String codeId);

    //코드 그룹 하위 코드 개수 조회
    Long countByCodeGroup_CodeGroupIdAndIsDeleted(String codeGroupId, boolean isDeleted);
}
