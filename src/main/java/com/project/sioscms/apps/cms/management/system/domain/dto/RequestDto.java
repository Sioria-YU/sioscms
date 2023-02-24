package com.project.sioscms.apps.cms.management.system.domain.dto;

import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestDto extends CommonSearchDto {
    /*아이디*/
    private String userId;
    /*성명*/
    private String name;
    /*성별*/
    private String gender;
}
