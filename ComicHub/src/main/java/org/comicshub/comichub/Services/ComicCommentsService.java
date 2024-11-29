package org.comicshub.comichub.Services;

import org.comicshub.comichub.Models.ComicComment;
import org.comicshub.comichub.Repositories.ComicCommentsRepository;
import org.comicshub.comichub.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ComicCommentsService {

    private ComicCommentsRepository comicCommentsRepository;

    @Autowired
    public ComicCommentsService(ComicCommentsRepository comicCommentsRepository, UserService userService) {
        this.comicCommentsRepository = comicCommentsRepository;
    }

    public List<ComicComment> findAll(){
        return comicCommentsRepository.findAll();
    }

    public List<ComicComment> findByComicId(long id){
        return this.comicCommentsRepository.findByComicId(id);
    }

    public void save(ComicComment comicComment){
        this.comicCommentsRepository.save(comicComment);
    }

}
