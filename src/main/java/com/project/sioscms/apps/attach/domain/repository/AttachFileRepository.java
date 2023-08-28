package com.project.sioscms.apps.attach.domain.repository;

import com.project.sioscms.apps.attach.domain.entity.AttachFile;
import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachFileRepository extends CommonJpaRepository<AttachFile, Long> {
    Optional<AttachFile> findByFileName(String fileName);
    List<AttachFile> findAllByAttachFileGroupAndIsDeleted(AttachFileGroup attachFileGroup, boolean isDeleted);

    //파일 순번 채번용
    Long countByAttachFileGroupAndIsDeleted(AttachFileGroup attachFileGroup, boolean isDeleted);
}
