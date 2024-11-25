package org.comicshub.comichub.Services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comicshub.comichub.Models.Genre;
import org.comicshub.comichub.Repositories.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenresService {

    @Autowired
    GenresRepository genresRepository;


    public Genre save(Genre genre) {
        return genresRepository.save(genre);
    }

    public List<Genre> findAll() {
        return genresRepository.findAll();
    }

    public Genre findById(long id){
        return genresRepository.findById(id);
    }

}
