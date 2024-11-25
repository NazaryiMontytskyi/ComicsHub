package org.comicshub.comichub.Repositories;

import org.comicshub.comichub.Models.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicsRepository extends JpaRepository<Comic, Long> {

    List<Comic> findAll();

    Comic save(Comic comic);

    Comic findById(long id);

}
