package com.project.sioscms.apps.menu.domain.repository;

import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.common.domain.repository.CommonJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends CommonJpaRepository<Menu,Long> {
    Optional<Menu> findTop1ByIsDeletedOrderByOrderNumDesc(boolean isDeleted);

    @Modifying
    @Query(value =
            "UPDATE Menu m " +
            "SET m.orderNum = m.orderNum + (:increaseNum) " +
            "WHERE m.orderNum >= :startOrderNum " +
            "AND m.orderNum <= :endOrderNum " +
            "AND m.orderNum <> :nowOrderNum " +
            "AND m.isDeleted = :isDeleted")
    void updateByOrders(Long nowOrderNum, Long startOrderNum, Long endOrderNum, Boolean isDeleted, Long increaseNum);
}
