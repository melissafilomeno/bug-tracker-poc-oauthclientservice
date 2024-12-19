package com.myorg.bugTrackerPoc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
public class BugController {

    private final RestClient restClient;

    public BugController(RestClient restClient){
        this.restClient = restClient;
    }

    @GetMapping("/")
    public List getBugs(){
        return restClient.get()
                .uri("http://localhost:9090/bugs")
                .retrieve()
                .toEntity(List.class).getBody();
    }
}
