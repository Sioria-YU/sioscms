package com.project.sioscms.common.utils.jpa.restriction;

import lombok.Getter;

import javax.persistence.criteria.JoinType;

/**
 * JPA Restriction Join Class
 */
@Getter
public class ChangSolJpaJoin {
    private final String columnName;

    private final JoinType joinType;

    public ChangSolJpaJoin(String columnName, JoinType joinType) {
        this.columnName = columnName;
        this.joinType = joinType;
    }
}
