package com.project.sioscms.common.domain.entity;

import com.project.sioscms.apps.account.domain.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CommonEntityWithId {
    @Comment("고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("등록자 pk")
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private Account createdBy;

    @Comment("수정자 pk")
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private Account updatedBy;
}
