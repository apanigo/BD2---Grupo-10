package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Client, Long> {
}
