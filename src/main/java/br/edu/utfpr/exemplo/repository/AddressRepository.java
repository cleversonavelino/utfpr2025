package br.edu.utfpr.exemplo.repository;

import br.edu.utfpr.exemplo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
