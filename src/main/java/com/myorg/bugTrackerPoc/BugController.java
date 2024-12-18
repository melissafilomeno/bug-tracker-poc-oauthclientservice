package com.myorg.bugTrackerPoc;

import com.myorg.bugTrackerPoc.openapi.client.model.Bug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class BugController {

    @Autowired
    WebClient webClient;

    @GetMapping("/")
    public Mono<Bug> getBug(){
        Flux<Bug> response = this.webClient.get()
                .uri("http://localhost:9090/bugs")
                .retrieve()
                .bodyToFlux(Bug.class);

        List<Bug> bugData = response.collectList().block();

        return Mono.just(bugData.get(0));
    }
}
