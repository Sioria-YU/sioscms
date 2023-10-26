package com.project.sioscms.apps.contents.domain.repository;

import com.project.sioscms.apps.contents.domain.entity.Contents;
import com.project.sioscms.apps.contents.domain.entity.ContentsHistory;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentsHistoryRepository extends CommonJpaRepository<ContentsHistory, Long> {
    Optional<ContentsHistory> findTopByContentsAndIsDeletedOrderByVersionDesc(Contents contents, Boolean isDeleted);
    Optional<ContentsHistory> findFirstByContentsAndIsUsedAndIsDeleted(Contents contents, Boolean isUsed, Boolean isDeleted);

}
