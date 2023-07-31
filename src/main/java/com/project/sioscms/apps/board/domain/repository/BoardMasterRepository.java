package com.project.sioscms.apps.board.domain.repository;

import com.project.sioscms.apps.board.domain.entity.BoardMaster;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMasterRepository extends CommonJpaRepository<BoardMaster, Long> {
}
