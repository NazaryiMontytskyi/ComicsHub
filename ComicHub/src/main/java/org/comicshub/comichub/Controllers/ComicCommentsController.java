package org.comicshub.comichub.Controllers;

import jakarta.validation.Valid;
import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.ComicComment;
import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.ComicCommentsService;
import org.comicshub.comichub.Services.ComicsService;
import org.comicshub.comichub.Services.UserService;
import org.comicshub.comichub.ValidationForms.CommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/comments")
public class ComicCommentsController {

    private ComicCommentsService comicCommentsService;
    private UserService userService;
    private ComicsService comicsService;

    @Autowired
    public ComicCommentsController(ComicCommentsService comicCommentsService, UserService userService, ComicsService comicsService) {
        this.comicCommentsService = comicCommentsService;
        this.userService = userService;
        this.comicsService = comicsService;
    }

    @PostMapping("/")
    public String postComment(
            @ModelAttribute("comment") @Valid CommentForm commentForm,
            BindingResult bindingResult,
            Model model,
            @RequestParam("comic_id") long comic_id,
            Principal principal
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/comics/" + comic_id;
        }

        ComicComment newComicComment = new ComicComment();
        newComicComment.setContent(commentForm.getComment());
        User authorOfComment = this.userService.getUserByPrincipal(principal);
        newComicComment.setAuthorOfComment(authorOfComment);
        Comic comicToComment = this.comicsService.findById(comic_id);
        newComicComment.setComic(comicToComment);
        this.comicCommentsService.save(newComicComment);
        return "redirect:/comics/" + comic_id;
    }

}
