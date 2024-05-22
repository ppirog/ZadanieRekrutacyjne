package org.zadanierekrutacyjne;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.zadanierekrutacyjne.infrastructure.errorvalidation.ApiValidationErrorResponseDto;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
class ZadanieRekrutacyjneApplicationTests {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void postgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void user_path() throws Exception {


        /* happy path
         * there is not any users in database
         * step 1 user made POST request to /register endpoint with not data and status is 400 with list of errors
         * step 2 user made POST request to /register endpoint with data someUser and somePassword and status is 201
         * step 3 user made POST request to /register endpoint with data someUser and somePassword and status is 409 with message "Login already exists"
         * step 4 user made POST request to /login endpoint with data someUser2 and somePassword and status is 401
         * step 5 user made POST request to /login endpoint with data someUser and somePassword and status is 200. response contains token
         * step 6 user made GET request to /find/{login} without token endpoint with token and status is 401. response contains username
         * step 7 user made GET request to /find/{login} with token endpoint with token and status is 200. response contains username
         * step 8 user made PUT request to /update/{login} without token endpoint with token and status is 401.
         * step 9 user made PUT request to /update/{login} with token endpoint with token with body someUser2 and status is 200. response contains someUser2
         * step 10 user made DELETE request to /delete/{login} without token endpoint with token and status is 401
         * step 11 user made DELETE request to /delete/{login} with token endpoint with token and status is 200. response contains deleted username
         * */



        //step 1 user made POST request to /register endpoint with not data and status is 400 with list of errors
        final MvcResult result5 = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {}
                                """))
                .andExpect(status().isBadRequest())
                .andReturn();

        final ApiValidationErrorResponseDto apiValidationErrorResponseDto = objectMapper.readValue(result5.getResponse().getContentAsString(), ApiValidationErrorResponseDto.class);
        final List<String> errors = apiValidationErrorResponseDto.errors();

        assertAll(
                () -> assertTrue(errors.contains("login cannot be null")),
                () -> assertTrue(errors.contains("login cannot be empty")),
                () -> assertTrue(errors.contains("login cannot be blank")),
                () -> assertTrue(errors.contains("password cannot be null")),
                () -> assertTrue(errors.contains("password cannot be empty")),
                () -> assertTrue(errors.contains("password cannot be blank"))
        );



        //step 2 user made POST request to /register endpoint with data someUser and somePassword and status is 201
        final MvcResult result = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "login": "someUser",
                                "password": "somePassword"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        assertAll(
                () -> assertThat(result.getResponse().getContentAsString()).contains("someUser"),
                () -> assertThat(result.getResponse().getContentAsString()).contains("REGISTERED")
        );



        //step 3 user made POST request to /register endpoint with data someUser and somePassword and status is 409 with message "Login already exists"
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "login": "someUser",
                                "password": "somePassword"
                                }
                                """))
                .andExpect(status().isConflict());



        //step 4 user made POST request to /login endpoint with data someUser2 and somePassword and status is 401
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "login": "someUser2",
                                "password": "somePassword"
                                }
                                """))
                .andExpect(status().isUnauthorized());



        //step 5 user made POST request to /login endpoint with data someUser and somePassword and status is 200. response contains token
        final MvcResult result1 = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "login": "someUser",
                                "password": "somePassword"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        final String token = objectMapper.readTree(result1.getResponse().getContentAsString()).get("token").asText();
        assertAll(
                () -> assertThat(token).matches(Pattern.compile("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$"))
        );



        //step 6 user made GET request to /find/{login} without token endpoint with token and status is 401. response contains username
        mockMvc.perform(MockMvcRequestBuilders.get("/find/someUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());



        //step 7 user made GET request to /find/{login} with token endpoint with token and status is 200. response contains username
        final MvcResult result2 = mockMvc.perform(get("/find/someUser")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String username = objectMapper.readTree(result2.getResponse().getContentAsString()).get("login").asText();
        assertAll(
                () -> assertEquals("someUser", username)
        );



        //step 8 user made PUT request to /update/{login} without token endpoint with token and status is 401.
        mockMvc.perform(MockMvcRequestBuilders.get("/update/someUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "login": "someUser2",
                                "password":"$2a$10$hUSgGkm15T9ksN5XZtwGBejfKjj4ytjvM1sC39pmp/5MnnZ.9ssvS"
                                }
                                """))
                .andExpect(status().isUnauthorized());



        //step 9 user made PUT request to /update/{login} with token endpoint with token with body someUser2 and status is 200. response contains someUser2
        final MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.put("/update/someUser")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "login": "someUser2",
                                "password":"$2a$10$hUSgGkm15T9ksN5XZtwGBejfKjj4ytjvM1sC39pmp/5MnnZ.9ssvS"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        final String username2 = objectMapper.readTree(result3.getResponse().getContentAsString()).get("login").asText();
        assertAll(
                () -> assertEquals("someUser2", username2)
        );



        //step 10 user made DELETE request to /delete/{login} without token endpoint with token and status is 401
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/someUser2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());




        //step 11 user made DELETE request to /delete/{login} with token endpoint with token and status is 200. response contains deleted username
        final MvcResult result4 = mockMvc.perform(MockMvcRequestBuilders.delete("/delete/someUser2")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String username3 = objectMapper.readTree(result4.getResponse().getContentAsString()).get("login").asText();
        assertAll(
                () -> assertEquals("someUser2", username3)
        );
    }
}