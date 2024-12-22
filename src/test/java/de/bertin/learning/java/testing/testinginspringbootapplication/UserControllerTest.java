package de.bertin.learning.java.testing.testinginspringbootapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * we intend to only test the Controller layer when using this annotation.
 * Like that we don't load the whole application context in contrast to @SpringbootTest
 */
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private User user;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // we want to replace the userService within the Spring ApplicationContext. So we use @MockitoBean instead of @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp(){
        user = new User("John Doe", "john.doe@gmail.com");
    }

    @Test
    void createUser() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                  {
                    "name": "John Doe",
                    "email": "john.doe@gmail.com"
                  }
                  """))
                .andExpect(status().isCreated())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        User userJson = objectMapper.readValue(
                mvcResult
                        .getResponse()
                        .getContentAsString(),
                User.class);

        assertEquals(user.getId(), userJson.getId());
        assertEquals(user.getName(), userJson.getName());
        assertEquals(user.getEmail(), userJson.getEmail());
        verify(userService).saveUser(any(User.class));
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@gmail.com"));

        verify(userService).getUserById(1L);
    }

    @Test
    void userNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1111"))
                .andExpect(status().isNotFound());

        verify(userService).getUserById(1111L);
    }
}