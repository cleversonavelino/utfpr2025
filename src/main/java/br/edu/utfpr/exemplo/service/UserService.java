package br.edu.utfpr.exemplo.service;

import br.edu.utfpr.exemplo.ExemploApplication;
import br.edu.utfpr.exemplo.exception.BusinessException;
import br.edu.utfpr.exemplo.model.Address;
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

    /*
    ACID
                                    TRANSACAO 1
    CONTA CORRENTE                  1 - DIGITAR O VALOR - 50
    ID NUMERO SALDO                 2 - CONSULTAR O SALDO 500
    1  123    500                                               TRANSACAO 2
                                                                1 - DIGITAR O VALOR - 50
                                                                2 - CONSULTAR O SALDO - 450
                                    3 - REALIZA O SAQUE - 50
    TRANSACOES                      4 - SALDO ATUAL     - 450
    ID TIPO VALOR                                               3 - REALIZA O SAQUE - 50
    1   SAQUE 50                                                4 - SALDO ATUAL - 400
    2   SAQUE 50



     */

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) throws BusinessException {
        user.getAddresses().forEach(address -> address.setUser(user));
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User user) throws BusinessException {
        if (user.getId() == null) {
            BusinessException ex = new BusinessException();
            ex.setMessage("O ID precisa ser preenchido.");
            ex.setCodeDescription("ID_REQUIRED");
            throw ex;
        }

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
