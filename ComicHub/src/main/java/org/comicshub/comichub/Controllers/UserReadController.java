package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.UserRead;
import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.ComicsService;
import org.comicshub.comichub.Services.UserReadService;
import org.comicshub.comichub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/userread")
@Controller
public class UserReadController {

    UserReadService userReadService;
    ComicsService comicsService;
    UserService userService;

    @Autowired
    public UserReadController(UserReadService userReadService, ComicsService comicsService, UserService userService) {
        this.userReadService = userReadService;
        this.comicsService = comicsService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public String addComicToFavorites(@RequestParam("comicId") long comicId, Principal principal){
        UserRead userRead = new UserRead();
        User user = this.userService.getUserByPrincipal(principal);
        Comic comic = this.comicsService.findById(comicId);

        userRead.setUser(user);
        userRead.setComic(comic);
        userReadService.save(userRead);

        return "redirect:/comics/index";
    }

    @DeleteMapping("/remove")
    public String removeComicFromFavourites(@RequestParam("comicId") long comicId, Principal principal){
        User user = this.userService.getUserByPrincipal(principal);
        Comic comic = this.comicsService.findById(comicId);
        this.userReadService.removeFromList(user, comic);
        return "redirect:/favourites";
    }

}
