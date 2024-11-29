package org.comicshub.comichub.Services;


import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Repositories.ComicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComicsService {

    ComicsRepository comicsRepository;
    UserService userService;

    @Autowired
    public ComicsService(ComicsRepository comicsRepository, UserService userService) {
        this.comicsRepository = comicsRepository;
        this.userService = userService;
    }

    @Transactional
    public List<Comic> findByUserId(long id){
        return this.comicsRepository.findByUserId(id);
    }

    @Transactional
    public List<Comic> index(){
        return this.comicsRepository.findAll();
    }

    public void save(Principal principal, Comic comic){
        comic.setUser(this.userService.getUserByPrincipal(principal));
        this.comicsRepository.save(comic);
    }

    @Transactional
    public Comic findById(final long id){
        return this.comicsRepository.findById(id);
    }



}
