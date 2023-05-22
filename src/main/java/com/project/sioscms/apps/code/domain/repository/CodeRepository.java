package com.project.sioscms.apps.code.domain.repository;

import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends CommonJpaRepository<Code, Long> {
    Optional<Code> findByCodeId(String codeId);
}
