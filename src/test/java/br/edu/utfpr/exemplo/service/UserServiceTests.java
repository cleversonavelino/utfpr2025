package br.edu.utfpr.exemplo.service;

import br.edu.utfpr.exemplo.exception.BusinessException;
import br.edu.utfpr.exemplo.model.Address;
import br.edu.utfpr.exemplo.model.User;
import br.edu.utfpr.exemplo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    public void testSave() throws BusinessException {
        List<Address> addresses = new ArrayList<>();
        User user = new User();
        user.setId(1L) ;
        user.setAddresses(addresses);

        when(userRepository.save(user)).thenReturn(user);

        user = userService.save(user);
        assertThat(user.getId()).isNotEqualTo(null);
    }

    @Test
    public void testSaveWithoutAddress() throws BusinessException {
        User user = new User();
        user.setId(1L) ;
        user.setAddresses(null);
        when(userRepository.save(user)).thenReturn(user);

        User userResult = null;
        try {
            userResult = userService.save(user);
        } catch (BusinessException e) {
            assertThat(e.getMessage()).contains("O Usuário precisa ter endereço");
        }
        assertThat(userResult).isNull();
    }
}
