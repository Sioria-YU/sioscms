package com.project.sioscms.apps.code.domain.repository;

import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends CommonJpaRepository<Code, Long> {

    //코드그룹, 코드아이디로 조회
    Optional<Code> findByCodePk_CodeGroupAndCodePk_CodeId(CodeGroup codeGroup, String codeId);

    //코드그룹 아이디, 코드 아이디로 조회
    Optional<Code> findByCodePk_CodeGroup_CodeGroupIdAndCodePk_CodeId(String codeGroupId, String codeId);

    //코드 그룹 하위 코드 개수 조회
    Long countByCodePk_CodeGroup_CodeGroupIdAndIsDeleted(String codeGroupId, boolean isDeleted);

    Optional<Code> findDistinctTop1ByCodePk_CodeGroup_CodeGroupIdOrderByOrderNumDesc(String codeGroupId);

    //코드그룹으로 전체 조회
    List<Code> findAllByCodePk_CodeGroup(CodeGroup codeGroup);
}
