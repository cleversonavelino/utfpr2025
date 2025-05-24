package br.edu.utfpr.exemplo.service;

import br.edu.utfpr.exemplo.ExemploApplication;
import br.edu.utfpr.exemplo.model.User;
import br.edu.utfpr.exemplo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
