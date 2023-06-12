package com.project.sioscms.apps.attach.domain.repository;


import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachFileGroupRepository extends CommonJpaRepository<AttachFileGroup, Long> {
}
