package com.project.sioscms.apps.account.domain.repository;

import com.project.sioscms.apps.account.domain.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Object> findById(long id);

    Optional<Account> findByUserId(String userId);

    List<Account> findAllByGenderAndIsDelete(boolean gender, boolean isDelete);

    boolean existsByUserId(String userId);
}
