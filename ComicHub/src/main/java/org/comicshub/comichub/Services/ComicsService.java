package org.comicshub.comichub.Services;


import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Repositories.ComicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Comic> index(){
        return this.comicsRepository.findAll();
    }

    public void save(Principal principal, Comic comic){
        comic.setUser(this.userService.getUserByPrincipal(principal));
        this.comicsRepository.save(comic);
    }

    public Comic findById(final long id){
        return this.comicsRepository.findById(id);
    }



}
