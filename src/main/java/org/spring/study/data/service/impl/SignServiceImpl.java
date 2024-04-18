package org.spring.study.data.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.study.common.CommonResponse;
import org.spring.study.config.security.JwtTokenProvider;
import org.spring.study.data.dto.SignInResultDto;
import org.spring.study.data.dto.SignUpResultDto;
import org.spring.study.data.entity.Users;
import org.spring.study.data.repository.UserRepository;
import org.spring.study.data.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignServiceImpl implements SignService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(String id, String password, String name, String role) throws RuntimeException {
        LOGGER.info("[SignServiceImpl] 회원 가입 정보 전달");
        Users users;
        if (role.equalsIgnoreCase("admin")) {
            users = Users.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            users = Users.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        Users savedUsers = userRepository.save(users);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        LOGGER.info("[SignServiceImpl] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUsers.getName().isEmpty()) {
            LOGGER.info("[SignServiceImpl] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[SignServiceImpl] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        LOGGER.info("[SignServiceImpl] signDataHandler 로 회원 정보 요청");
        Users user = userRepository.getByUid(id);
        LOGGER.info("[SignServiceImpl] Id : {}", id);

        LOGGER.info("[SignServiceImpl] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        LOGGER.info("[SignServiceImpl] 패스워드 일치");

        LOGGER.info("[SignServiceImpl] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()),
                        user.getRoles()))
                .build();

        LOGGER.info("[SignServiceImpl] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMessage(CommonResponse.FAIL.getMsg());
    }
}
