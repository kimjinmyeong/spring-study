package org.spring.study.data.entity;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("blackList")
@Getter
@Builder
public class BlackList {

    @Id
    private String token;

}
