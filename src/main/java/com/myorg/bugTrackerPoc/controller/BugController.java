package com.myorg.bugTrackerPoc.controller;

import com.myorg.bugTrackerPoc.openapi.client.model.Bug;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RestController
public class BugController {

    private final RestClient restClient;

    @Value("${get-bugs-uri}")
    private String getBugsUri;

    public BugController(RestClient restClient){
        this.restClient = restClient;
    }

    @GetMapping("/")
    public List<Bug> getBugs(){
        return restClient.get()
                .uri(getBugsUri)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Bug>>() {})
                .getBody();
    }
}
