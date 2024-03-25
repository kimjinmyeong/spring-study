package org.spring.study.data.service.impl;

import org.spring.study.data.dto.MemberDto;
import org.spring.study.data.service.WebClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientServiceImpl implements WebClientService {

    private final String BASE_URL = "http://localhost:9090";

    @Override
    public String getName() {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .uri("api/v1/crud-api")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String getNameWithPathVariable() {
        WebClient webClient = WebClient.create(BASE_URL);

        ResponseEntity<String> responseEntity = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/{name}").build("Flature"))
                .retrieve()
                .toEntity(String.class)
                .block();

        return responseEntity.getBody();
    }

    @Override
    public String getNameWithParameter() {
        WebClient webClient = WebClient.create(BASE_URL);

        return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api").queryParam("name", "Flature").build())
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                })
                .block();
    }

    @Override
    public ResponseEntity<MemberDto> postWithParamAndBody() {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDto memberDto = MemberDto.builder()
                .name("flature!!")
                .email("flature@gmail.com")
                .organization("Around Hub Studio")
                .build();

        return webClient.post().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api")
                        .queryParam("name", "Flature")
                        .queryParam("email", "flature@test.co.kr")
                        .queryParam("organization", "SpringStudy")
                        .build())
                .bodyValue(memberDto)
                .retrieve()
                .toEntity(MemberDto.class)
                .block();
    }

    @Override
    public ResponseEntity<MemberDto> postWithHeader() {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDto memberDto = MemberDto.builder()
                .name("flature!!")
                .email("flature@gmail.com")
                .organization("SpringStudy")
                .build();

        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("api/v1/crud-api/add-header")
                        .build())
                .bodyValue(memberDto)
                .header("my-header", "SpringStudy API")
                .retrieve()
                .toEntity(MemberDto.class)
                .block();

    }
}
