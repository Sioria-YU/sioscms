package com.project.sioscms.apps.code.domain.repository;

import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeGroupRepository extends CommonJpaRepository<CodeGroup, String> {

    Optional<CodeGroup> findByCodeGroupId(String codeGroup);
}
