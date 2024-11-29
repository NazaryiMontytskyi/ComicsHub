package org.comicshub.comichub.Repositories;

import jakarta.transaction.Transactional;
import org.comicshub.comichub.Models.ImageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageContent, Long> {

    @Transactional
    List<ImageContent> findAll();

    @Transactional
    ImageContent findById(long id);

    ImageContent save(ImageContent imageContent);

    ImageContent deleteById(long id);

}
