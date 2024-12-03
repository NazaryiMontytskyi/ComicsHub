package org.comicshub.comichub.Repositories;

import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.UserRead;
import org.comicshub.comichub.Security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReadRepository extends JpaRepository<UserRead, Long> {

    List<UserRead> findByUser(User user);

    @Modifying
    @Query("DELETE FROM UserRead ur WHERE ur.user = :user AND ur.comic = :comic")
    void deleteByUserAndComic(@Param("user") User user, @Param("comic") Comic comic);

    UserRead save(UserRead userRead);
}
