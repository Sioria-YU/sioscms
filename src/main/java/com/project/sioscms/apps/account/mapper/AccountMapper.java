package com.project.sioscms.apps.account.mapper;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    AccountDto.Response toResponse(final Account user);
}
