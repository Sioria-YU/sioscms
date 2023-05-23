package com.project.sioscms.cms.management.system.domain.dto;

import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CodeSearchDto extends CommonSearchDto {
    /* 코드그룹 아이디 */
    private String codeGroupId;
    /* 코드그룹 명 */
    private String codeGroupLabel;
    /* 코드 아이디 */
    private String codeId;

}
