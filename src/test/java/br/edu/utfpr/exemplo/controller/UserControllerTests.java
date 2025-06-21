package br.edu.utfpr.exemplo.controller;

import br.edu.utfpr.exemplo.model.Address;
import br.edu.utfpr.exemplo.model.User;
import br.edu.utfpr.exemplo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void testGetEndpoint() throws Exception {
        User user = new User();
        user.setEmail("email@email.com");
        List<User> users = new ArrayList<>();
        users.add(user);
        // Given
        when(userService.findAll()).thenReturn(users);

        // When/Then
        mockMvc.perform(get("/api/user")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].email").
                        value("email@email.com"));
    }

    @Test
    public void testPostEndpoint() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@email.com");

        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        addresses.add(address);

        user.setAddresses(addresses);

        // Given
        when(userService.save(user)).thenReturn(user);

        // When/Then
        mockMvc.perform(post("/api/user")
                        .with(user("admin").roles("ADMIN"))
                        .contentType("application/json")
                        .content(asJsonString(user))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").
                        value("email@email.com"));
    }

    @Test
    public void testPostEndpointWithBusinessException() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@email.com");

        // Given
        when(userService.save(user)).thenReturn(user);

        // When/Then
        mockMvc.perform(post("/api/user")
                        .with(user("admin").roles("ADMIN"))
                        .contentType("application/json")
                        .content(asJsonString(user))
                )
                .andExpect(status().isBadRequest());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
