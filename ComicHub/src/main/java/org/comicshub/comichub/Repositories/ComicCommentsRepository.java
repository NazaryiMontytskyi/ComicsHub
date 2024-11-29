package org.comicshub.comichub.Repositories;

import org.comicshub.comichub.Models.ComicComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicCommentsRepository extends JpaRepository<ComicComment, Long> {

    List<ComicComment> findAll();

    List<ComicComment> findByComicId(Long id);

    ComicComment save(ComicComment comicComment);


}
