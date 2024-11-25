package org.comicshub.comichub.Services;


import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Repositories.ComicsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComicsService {

    ComicsRepository comicsRepository;

    public ComicsService(ComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }

    public List<Comic> index(){
        return this.comicsRepository.findAll();
    }

    public void save(final Comic comic){
        this.comicsRepository.save(comic);
    }

    public Comic findById(final long id){
        return this.comicsRepository.findById(id);
    }



}
