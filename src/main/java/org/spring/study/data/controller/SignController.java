package org.spring.study.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.study.data.dto.SignInResultDto;
import org.spring.study.data.dto.SignUpResultDto;
import org.spring.study.data.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(
            @Parameter(description = "ID", required = true) @RequestParam String id,
            @Parameter(description = "Password", required = true) @RequestParam String password)
            throws RuntimeException {
        LOGGER.info("[SignController] 로그인 시도: id : {}, pw : ****", id);
        SignInResultDto signInResultDto = signService.signIn(id, password);

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[SignController] 로그인 성공: id : {}, token : {}", id,
                    signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(
            @Parameter(description = "아이디", required = true) @RequestParam String id,
            @Parameter(description = "비밀번호", required = true) @RequestParam String password,
            @Parameter(description = "이름", required = true) @RequestParam String name,
            @Parameter(description = "권한", required = true) @RequestParam String role) {
        LOGGER.info("[SignController] 회원가입 수행: id : {}, password : ****, name : {}, role : {}", id,
                name, role);
        SignUpResultDto signUpResultDto = signService.signUp(id, password, name, role);

        LOGGER.info("[SignController] 회원가입 완료: id : {}", id);
        return signUpResultDto;
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}