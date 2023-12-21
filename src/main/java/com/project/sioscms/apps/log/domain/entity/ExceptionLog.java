package com.project.sioscms.apps.log.domain.entity;

import com.project.sioscms.apps.log.domain.dto.ExceptionLogDto;
import com.project.sioscms.apps.log.mapper.ExceptionLogMapper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ExceptionLog {
    @Comment("고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //시퀀스 생성 규칙
    private Long id;

    @Comment("등록일시")
    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(updatable = false)  //데이터 수정 방지
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Comment("유저 아이디")
    @NotNull
    private String userId;

    @Comment("접속 IP")
    private String connectedIp;

    @Comment("접속 Url")
    private String connectedUrl;

    @Comment("예외 명")
    private String exceptionName;

    @Comment("예외 기록")
    private String exceptionRecord;

    public ExceptionLogDto.Response toResponse() { return ExceptionLogMapper.mapper.toResponse(this);
    }
}
