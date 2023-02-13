package com.project.sioscms.apps.account.domain.entity;


import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.mapper.AccountMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWhidIdAndDate;
import com.sun.istack.NotNull;
import org.hibernate.type.CharacterType;

import javax.persistence.Entity;

@Entity
public class Account extends CommonEntityWhidIdAndDate {

    @NotNull
    private String userId;
    private String userPassword;
    private String name;
    private Character gender;
    private String phone;
    private String address;
    private String addressDetail;
    private String zip;
    private String roadAddress;
    private String roadAddressDetail;
    private String brithday;

    public AccountDto.Response toResponse(){
        return AccountMapper.mapper.toResponse(this);
    }


}
