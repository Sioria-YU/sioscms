package com.project.sioscms.common.mapper;

public interface CommonEntityMapper<Entity, Request, Response> {
    Response toResponse(final Entity entity);
    Entity toEntity(final Request dto);
}
