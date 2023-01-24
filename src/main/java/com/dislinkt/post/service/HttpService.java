package com.dislinkt.post.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService {
    private final RestTemplate restTemplate;

    @Value("${dislinkt.auth.apikey}")
    private String apiKey;

    @Value("${dislinkt.auth.host}")
    private String authHost;

    @Value("${dislinkt.auth.port}")
    private String authPort;

    public HttpService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void activateUser(UUID userId) {
        String apiUrl = this.authHost + ":" + this.authPort;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", this.apiKey);
        Map<String, String> param = new HashMap<String, String>();
        param.put("id", userId.toString());
        HttpEntity<Void> requestEntity = new HttpEntity<Void>(null, headers);
        restTemplate.exchange(apiUrl + "/auth/activate?id={id}", HttpMethod.PUT, requestEntity, Void.class, param);
    }

    public List<UUID> getUserIdsByUsername(String keyword) {
        String apiUrl = this.authHost + ":" + this.authPort;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", this.apiKey);
        Map<String, String> param = new HashMap<String, String>();
        param.put("keyword", keyword);
        HttpEntity<Void> requestEntity = new HttpEntity<Void>(null, headers);
        return Arrays.asList(restTemplate.exchange(apiUrl + "/auth/search?keyword={keyword}", HttpMethod.GET, requestEntity, UUID[].class, param).getBody());
    }
}