package com.project.sioscms.apps.hashtag.domain.repository;

import com.project.sioscms.apps.hashtag.domain.entity.Hashtag;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;

import java.util.Optional;

public interface HashtagRepository extends CommonJpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByHashtagNameAndIsDeleted(String hashtagName, boolean isDeleted);
}
