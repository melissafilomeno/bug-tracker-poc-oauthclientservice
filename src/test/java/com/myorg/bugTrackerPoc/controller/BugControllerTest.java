package com.myorg.bugTrackerPoc.controller;

import com.myorg.bugTrackerPoc.openapi.client.model.Bug;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BugControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestClient restClient;

    @Test
    public void getBugs_Success() throws Exception {
        String id = "2b1979d2-f24c-4cac-8b71-93734ec25a7d";
        String description = "This is my first bug";

        Bug bug = new Bug();
        bug.setId(id);
        bug.setDescription(description);

        List<Bug> bugs = new ArrayList<>();
        bugs.add(bug);

        ResponseEntity<List<Bug>> mockResponse = new ResponseEntity<>(bugs, HttpStatus.OK);

        RestClient.RequestHeadersUriSpec requestSpecMock = mock(RestClient.RequestHeadersUriSpec.class);
        when(restClient.get()).thenReturn(requestSpecMock);

        RestClient.RequestHeadersSpec requestHeadersSpecMock = mock(RestClient.RequestHeadersSpec.class);
        when(requestSpecMock.uri(any(String.class))).thenReturn(requestHeadersSpecMock);

        RestClient.RequestHeadersSpec requestHeadersSpecMock2 = mock(RestClient.RequestHeadersSpec.class);
        when(requestHeadersSpecMock.attributes(any(Consumer.class))).thenReturn(requestHeadersSpecMock2);

        RestClient.ResponseSpec responseSpecMock = mock(RestClient.ResponseSpec.class);
        when(requestHeadersSpecMock2.retrieve()).thenReturn(responseSpecMock);

        when(responseSpecMock.toEntity(any(ParameterizedTypeReference.class))).thenReturn(mockResponse);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].description").value(description));
        verify(restClient).get();
    }
}
