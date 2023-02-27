package com.project.sioscms.apps.menu.domain.repository;

import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CommonJpaRepository<Menu,Long> {

}
