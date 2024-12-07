package org.comicshub.comichub.Repositories;

import org.comicshub.comichub.Security.Role;
import org.comicshub.comichub.Security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findAllByRole(@Param("role") Role role);

    User deleteById(long id);

    boolean existsByUsername(String username);

}
