package com.project.sioscms.apps.account.domain.repository;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CommonJpaRepository<Account, Long> {
    Optional<Object> findById(long id);

    Optional<Account> findByUserId(String userId);

    List<Account> findAllByIsDeleteAndRole(boolean isDelete, Account.Role_Type role);

//    List<Account> findAllByGenderAndIsDelete(boolean gender, boolean isDelete);

    boolean existsByUserId(String userId);

}
