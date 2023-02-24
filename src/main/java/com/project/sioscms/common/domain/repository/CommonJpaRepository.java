package com.project.sioscms.common.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonJpaRepository<DOMAIN, ID_TYPE> extends JpaRepository<DOMAIN, ID_TYPE>, JpaSpecificationExecutor<DOMAIN> {

}
