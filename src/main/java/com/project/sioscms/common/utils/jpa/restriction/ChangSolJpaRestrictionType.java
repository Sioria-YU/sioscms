package com.project.sioscms.common.utils.jpa.restriction;

/**
 * Predicate WHERE 절 AND, OR TYPE
 */
public enum ChangSolJpaRestrictionType {
    AND("AND"),
    OR("OR");

    public final String title;

    ChangSolJpaRestrictionType(String title) {
        this.title = title;
    }
}
