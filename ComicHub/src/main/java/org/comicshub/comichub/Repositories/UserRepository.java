package org.comicshub.comichub.Repositories;

import org.comicshub.comichub.Security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    User findByUsername(String username);

    User findByEmail(String email);



}
