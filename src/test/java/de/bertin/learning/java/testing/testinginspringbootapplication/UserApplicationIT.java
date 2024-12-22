package de.bertin.learning.java.testing.testinginspringbootapplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApplicationIT {
    // we use TestRestTemplate to interact with the application just like
    // a real client would.
    @Autowired
    private TestRestTemplate  testRestTemplate;

    @Test
    void createUser() throws JsonProcessingException {
        HttpUserEntity httpUserEntity = givenCreatedUser();

        ResponseEntity<User> response = testRestTemplate.postForEntity("/users", httpUserEntity.request(), User.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(httpUserEntity.user().getName(), response.getBody().getName());
        assertEquals(httpUserEntity.user().getEmail(), response.getBody().getEmail());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void createAndRetrieveUser() throws JsonProcessingException {
        HttpUserEntity httpUserEntity = givenCreatedUser();

        ResponseEntity<User> savedUserResponse = testRestTemplate.postForEntity("/users", httpUserEntity.request(), User.class);

        assertNotNull(savedUserResponse.getBody());

        ResponseEntity<User> fetchedUserResponse = testRestTemplate.getForEntity("/users/" + savedUserResponse.getBody().getId(), User.class);
        assertEquals(HttpStatus.OK, fetchedUserResponse.getStatusCode());
        assertNotNull(fetchedUserResponse.getBody());
        assertEquals(httpUserEntity.user().getName(), fetchedUserResponse.getBody().getName());
        assertEquals(httpUserEntity.user().getEmail(), fetchedUserResponse.getBody().getEmail());
        assertEquals(savedUserResponse.getBody().getId(), fetchedUserResponse.getBody().getId());
    }

    @Test
    void userNotFound() {
        ResponseEntity<User> response = testRestTemplate.getForEntity("/users/999", User.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    private static HttpUserEntity givenCreatedUser() throws JsonProcessingException {
        // set up the request to run to the running service
        User user = new User("John Doe", "john.doe@gmail.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
        return new HttpUserEntity(user, request);
    }

    private record HttpUserEntity(User user, HttpEntity<String> request) {
    }
}
