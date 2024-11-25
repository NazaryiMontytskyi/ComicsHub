package org.comicshub.comichub.Repositories;


import jakarta.transaction.Transactional;
import org.comicshub.comichub.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long> {

    @Override
    List<Genre> findAll();

    Genre save(Genre genre);

    Genre removeById(Long id);

    Genre findById(long id);

    Genre findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Genre g SET g.name = :name WHERE g.id = :id")
    void updateGenre(@Param("id") Long id, @Param("name") String name);

}
