package com.project.sioscms.apps.log.domain.repository;

import com.project.sioscms.apps.log.domain.entity.LoginLog;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends CommonJpaRepository<LoginLog, Long> {

}
