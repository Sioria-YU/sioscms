package com.project.sioscms.apps.hashtag.domain.repository;

import com.project.sioscms.apps.hashtag.domain.entity.BoardHashtag;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardHashtagRepository extends CommonJpaRepository<BoardHashtag, Long> {
}
