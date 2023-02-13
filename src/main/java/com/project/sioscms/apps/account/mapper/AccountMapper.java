package com.project.sioscms.apps.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);
}
