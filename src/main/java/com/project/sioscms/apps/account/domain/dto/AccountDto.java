package com.project.sioscms.apps.account.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountDto {

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
        private String state;
    }

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
        private String state;
    }
}
