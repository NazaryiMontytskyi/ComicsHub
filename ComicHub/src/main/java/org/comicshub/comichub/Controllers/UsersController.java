package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.ComicsService;
import org.comicshub.comichub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UsersController {

    private final UserService userService;
    private final ComicsService comicsService;

    @Autowired
    public UsersController(UserService userService, ComicsService comicsService) {

        this.userService = userService;
        this.comicsService = comicsService;
    }

    @GetMapping("/registration")
    public String registration(){
        return "security/registration";
    }

    @GetMapping("/login")
    public String login(){
        return "security/login";
    }

    @PostMapping("/registration")
    public String createUser(User user){
        this.userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/hello")
    public String hello(){
        return "security/hello";
    }

    @GetMapping("/my_account")
    public String myAccountPage(Model model, Principal principal){
        User user = this.userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("comics", this.comicsService.findByUserId(user.getId()));
        return "user/user-info";
    }

    @PatchMapping("/username")
    public String updateUsername(@RequestParam("username") String username, @RequestParam("user_id") long id){
        //TODO: validation of existing user
        User userToUpdate = this.userService.findById(id);
        userToUpdate.setUsername(username);
        this.userService.updateUser(userToUpdate);
        return "redirect:/login";
    }

}
