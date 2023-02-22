package com.project.sioscms.apps.account.domain.entity;


import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.mapper.AccountMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Account extends CommonEntityWithIdAndDate {

    public enum Role_Type {
        ADMIN, //관리자 0
        USER, //사용자 1
    }

    @NotNull
    @Column(unique=true)
    @Comment("유저 아이디")
    private String userId;

    @Comment("유저 비밀번호")
    private String userPassword;

    @Comment("유저 이름")
    private String name;

    @Column(length = 1)
    @Size(max = 1)
    @Comment("유저 성별")
    private String gender;

    @Comment("유저 휴대번호")
    private String phone;

    @Comment("유저 주소")
    private String address;

    @Comment("유저 주소 상세")
    private String addressDetail;

    @Comment("유저 우편번호")
    private String zip;

    @Comment("유저 도로명 주소")
    private String roadAddress;

    @Comment("유저 도로명 주소 상세")
    private String roadAddressDetail;

    @Comment("유저 생년월일")
    private String birthday;

    @ColumnDefault(value = "FALSE")
    @Comment("유저 삭제여부")
    private Boolean isDelete = false;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Role_Type role;

    @Column(length = 1)
    @Size(max = 1)
    @Comment("유저 상태")
    private String state;

    public AccountDto.Response toResponse(){
        return AccountMapper.mapper.toResponse(this);
    }


}
