package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
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

}
