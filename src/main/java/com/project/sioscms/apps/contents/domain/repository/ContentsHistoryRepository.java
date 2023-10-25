package com.project.sioscms.apps.contents.domain.repository;

import com.project.sioscms.apps.contents.domain.entity.ContentsHistory;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsHistoryRepository extends CommonJpaRepository<ContentsHistory, Long> {}
