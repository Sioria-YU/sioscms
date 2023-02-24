package com.project.sioscms.apps.account.domain.repository;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CommonJpaRepository<Account, Long> {
    Optional<Object> findById(long id);

    Optional<Account> findByUserId(String userId);

//    List<Account> findAllByIsDeleteAndRole(boolean isDelete, Account.Role_Type role);

    boolean existsByUserId(String userId);

}
