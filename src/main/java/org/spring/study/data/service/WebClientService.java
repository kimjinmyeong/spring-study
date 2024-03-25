package org.spring.study.data.service;

import org.spring.study.data.dto.MemberDto;
import org.springframework.http.ResponseEntity;

public interface WebClientService {

    String getName();

    String getNameWithPathVariable();

    String getNameWithParameter();

    ResponseEntity<MemberDto> postWithParamAndBody();

    ResponseEntity<MemberDto> postWithHeader();

}
