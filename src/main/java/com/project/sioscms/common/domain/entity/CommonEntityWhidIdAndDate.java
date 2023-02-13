package com.project.sioscms.common.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class CommonEntityWhidIdAndDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @CreatedBy
    Long createdBy;

    @LastModifiedBy
    Long updatedBy;

    @CreatedDate
    LocalDateTime createdOn = LocalDateTime.now();

    @LastModifiedDate
    LocalDateTime updatedOn = LocalDateTime.now();
}
