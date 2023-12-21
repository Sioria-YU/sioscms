package com.project.sioscms.apps.log.domain.repository;

import com.project.sioscms.apps.log.domain.entity.ExceptionLog;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepository extends CommonJpaRepository<ExceptionLog, Long> {
}
