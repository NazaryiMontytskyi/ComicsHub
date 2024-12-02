package org.comicshub.comichub.Repositories;

import org.comicshub.comichub.Models.UserRead;
import org.comicshub.comichub.Security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReadRepository extends JpaRepository<UserRead, Long> {

    List<UserRead> findByUser(User user);

    UserRead save(UserRead userRead);
}
