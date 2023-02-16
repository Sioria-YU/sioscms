package com.project.sioscms.apps.account.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class AccountDto {

    @Data
    public static class Request{
        private Long id;
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
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
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
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
