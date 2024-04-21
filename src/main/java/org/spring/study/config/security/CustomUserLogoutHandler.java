package org.spring.study.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.study.data.entity.BlackList;
import org.spring.study.data.repository.RedisRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserLogoutHandler implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomUserLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            LOGGER.info("로그아웃 시도 : {}", token);
            BlackList blackList = BlackList.builder()
                    .token(token)
                    .build();
            redisRepository.save(blackList);
            LOGGER.info("로그아웃 성공");
        } else {
            LOGGER.info("로그아웃 실패, 토큰 값을 확인해주세요.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
