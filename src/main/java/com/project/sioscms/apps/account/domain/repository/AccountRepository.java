package com.project.sioscms.apps.account.domain.repository;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CommonJpaRepository<Account, Long> {
    Optional<Object> findById(long id);

    Optional<Account> findByUserId(String userId);

//    List<Account> findAllByIsDeleteAndRole(boolean isDelete, Account.Role_Type role);

    long countAccountByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Account a " +
            "SET a.isDelete = true" +
            ", a.updatedDateTime = :updatedDateTime" +
            ", a.updatedBy = :updatedById " +
            "WHERE a.id IN(:idList)")
    void updateAllByIds(List<Long> idList, LocalDateTime updatedDateTime, Long updatedById);
}
