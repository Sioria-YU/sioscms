package com.project.sioscms.apps.log.domain.entity;

import com.project.sioscms.apps.log.domain.dto.LoginLogDto;
import com.project.sioscms.apps.log.mapper.LoginLogMapper;
import com.project.sioscms.common.domain.entity.CommonEntityWithIdAndDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class LoginLog {
    @Comment("고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //시퀀스 생성 규칙
    private Long id;

    @Comment("등록일시")
    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(updatable = false)  //데이터 수정 방지
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Comment("로그인 성공여부")
    @NotNull
    private Boolean isSuccess;

    @Comment("유저 아이디")
    @NotNull
    private String userId;

    @Comment("접속 IP")
    private String connectedIp;

    public LoginLogDto.Response toResponse() {
        return LoginLogMapper.mapper.toResponse(this);
    }
}
