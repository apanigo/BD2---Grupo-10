package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getUserByEmail(@Param("email") String email);
}
