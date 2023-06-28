package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
	
	Iterable<Client> findDistinctByUsernameIn(List<String> usernames);

    Optional<Client> findByUsername(String username);	
    
    List<Client> findDistinctByOrdersTotalPriceGreaterThanEqual(float number);
	
}
