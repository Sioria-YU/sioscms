package com.project.sioscms.apps.contents.domain.repository;

import com.project.sioscms.apps.contents.domain.entity.Contents;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends CommonJpaRepository<Contents, Long> {
    Long countByContentsName(final String contentsName);
}
