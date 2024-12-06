package org.comicshub.comichub.Services;


import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.Country;
import org.comicshub.comichub.Models.Genre;
import org.comicshub.comichub.Models.UserRead;
import org.comicshub.comichub.Repositories.ComicsRepository;
import org.comicshub.comichub.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComicsService {

    ComicsRepository comicsRepository;
    UserService userService;
    UserReadService userReadService;

    @Autowired
    public ComicsService(ComicsRepository comicsRepository, UserService userService, UserReadService userReadService) {
        this.comicsRepository = comicsRepository;
        this.userService = userService;
        this.userReadService = userReadService;
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

    @Transactional
    public List<Comic> findUserFavourites(long userId){
        User targetUser = this.userService.findById(userId);
        List<Comic> favourites = new ArrayList<>();
        List<UserRead> userComic = this.userReadService.findAllByUser(targetUser);
        for (UserRead userRead : userComic) {
            favourites.add(this.comicsRepository.findById(userRead.getComic().getId()));
        }
        return favourites;
    }

    @Transactional
    public Page<Comic> getFilteredComics(Genre genre, Country country, Pageable pageable){
        if(genre != null && country != null){
            return this.comicsRepository.findByGenreAndCountry(genre, country, pageable);
        }
        else if(genre != null){
            return this.comicsRepository.findByGenre(genre, pageable);
        }
        else if(country != null){
            return this.comicsRepository.findByCountry(country, pageable);
        }
        else{
            return this.comicsRepository.findAll(pageable);
        }

    }

    @Transactional
    public Page<Comic> getApproximateSearchComic(String searchQuery, Pageable pageable){
        return this.comicsRepository.findByTitleContainingIgnoreCase(searchQuery, pageable);
    }



}
