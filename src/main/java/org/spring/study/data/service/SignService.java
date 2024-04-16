package org.spring.study.data.service;

import org.spring.study.data.dto.SignInResultDto;
import org.spring.study.data.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(String id, String password, String name, String role) throws RuntimeException;

    SignInResultDto signIn(String id, String password) throws RuntimeException;

}
