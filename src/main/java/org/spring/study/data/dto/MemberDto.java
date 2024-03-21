package org.spring.study.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MemberDto {

    private String name;

    private String email;

    private String organization;

}
