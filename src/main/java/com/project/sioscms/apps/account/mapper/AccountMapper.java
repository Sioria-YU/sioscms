package com.project.sioscms.apps.account.mapper;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.mapper.CommonEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper extends CommonEntityMapper<Account, AccountDto.Request, AccountDto.Response> {
    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Override
    AccountDto.Response toResponse(final Account user);

    @Override
    Account toEntity(final AccountDto.Request dto);
}
