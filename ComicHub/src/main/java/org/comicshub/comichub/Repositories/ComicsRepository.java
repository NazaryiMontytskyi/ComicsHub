package org.comicshub.comichub.Repositories;

import jakarta.transaction.Transactional;
import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.Country;
import org.comicshub.comichub.Models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicsRepository extends JpaRepository<Comic, Long> {

    List<Comic> findAll();

    Comic save(Comic comic);

    Comic findById(long id);

    List<Comic> findByUserId(long id);

    Page<Comic> findByGenreAndCountry(Genre genre, Country country, Pageable pageable);

    Page<Comic> findByGenre(Genre genre, Pageable pageable);

    Page<Comic> findByCountry(Country country, Pageable pageable);

    Page<Comic> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
