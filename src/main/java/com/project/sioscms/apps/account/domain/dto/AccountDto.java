package com.project.sioscms.apps.account.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountDto {

    public static class Request{
        private Long id;
        private String createdBy;
        private String updatedBy;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
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
    }

    public static class Response{
        private Long id;
        private String createdBy;
        private String updatedBy;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
        private String userId;
        private String name;
        private Character gender;
        private String phone;
        private String address;
        private String addressDetail;
        private String zip;
        private String roadAddress;
        private String roadAddressDetail;
        private String brithday;
    }
}
