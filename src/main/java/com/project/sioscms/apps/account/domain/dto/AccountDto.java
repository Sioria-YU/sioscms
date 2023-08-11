package com.project.sioscms.apps.account.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import lombok.Data;

import java.time.LocalDateTime;

public class AccountDto {

    @Data
    public static class Request{
        private Long id;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
        private String userId;
        private String userPassword;
        private String name;
        private String gender;
        private String phone;
        private String address;
        private String addressDetail;
        private String zip;
        private String roadAddress;
        private String roadAddressDetail;
        private String birthday;
        private Boolean isDelete;
        private Account.Role_Type role;
        private String state;
    }

    @Data
    public static class Response{
        private Long id;
        private Account createdBy;
        private Account updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTime;
        private String userId;
        private String name;
        private String gender;
        private String phone;
        private String address;
        private String addressDetail;
        private String zip;
        private String roadAddress;
        private String roadAddressDetail;
        private String birthday;
        private Boolean isDelete;
        private Account.Role_Type role;
        private String state;
    }
}
