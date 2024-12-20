package com.myorg.bugTrackerPoc.integrationTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OAuthClientServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@Container
	private final static WireMockContainer resourceServerWireMockContainer =
			new WireMockContainer("wiremock/wiremock:2.35.0")
					.withMappingFromResource("resourceServerMockResponse", OAuthClientServiceApplicationTests.class, "resourceServerMockResponse.json");

	@Container
	private final static WireMockContainer oauthServerWireMockContainer =
			new WireMockContainer("wiremock/wiremock:2.35.0")
					.withMappingFromResource("oauthServerMockResponse", OAuthClientServiceApplicationTests.class, "oauthServerMockResponse.json");

	@BeforeEach
	public void setUp(){
		RestAssured.baseURI = "http://localhost:" + port;
	}

	@BeforeAll
	public static void beforeAll(){
		resourceServerWireMockContainer.start();
		oauthServerWireMockContainer.start();
	}

	@AfterAll
	public static void afterAll(){
		resourceServerWireMockContainer.stop();
		oauthServerWireMockContainer.stop();
	}

	@DynamicPropertySource
	public static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("get-bugs-uri", resourceServerWireMockContainer::getBaseUrl);
		dynamicPropertyRegistry.add("spring.security.oauth2.client.provider.keycloak.token-uri", oauthServerWireMockContainer::getBaseUrl);
	}

	@Test
	void getAllBugs_Success() {
		given()
				.get("/")
		.then()
				.body(".", hasSize(1));

	}

}
