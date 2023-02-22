package com.project.sioscms.apps.account.domain.repository;

import com.project.sioscms.apps.account.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Object> findById(long id);

    Optional<Account> findByUserId(String userId);

    List<Account> findAllByGenderAndIsDelete(boolean gender, boolean isDelete);

    boolean existsByUserId(String userId);

}
