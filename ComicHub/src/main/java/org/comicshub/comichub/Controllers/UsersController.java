package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Models.ImageContent;
import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.ComicsService;
import org.comicshub.comichub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class UsersController {

    private final UserService userService;
    private final ComicsService comicsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserService userService, ComicsService comicsService, PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.comicsService = comicsService;
        this.passwordEncoder = passwordEncoder;
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

    @PatchMapping("/email")
    public String updateEmail(@RequestParam("email") String email, @RequestParam("user_id") long id){
        //TODO: validation of existing user
        User userToUpdate = this.userService.findById(id);
        userToUpdate.setEmail(email);
        this.userService.updateUser(userToUpdate);
        return "redirect:/login";
    }

    @PatchMapping("/avatar")
    public String updateAvatar(@RequestParam("avatar") MultipartFile avatar, @RequestParam("user_id") long id) throws IOException {
        //TODO: validation of existing user
        User userToUpdate = this.userService.findById(id);
        ImageContent newAvatar = new ImageContent();
        newAvatar.fromMultipartFile(avatar);
        userToUpdate.setAvatar(newAvatar);
        this.userService.updateUser(userToUpdate);
        return "redirect:/login";
    }


    @PatchMapping("/password")
    public String updatePassword(@RequestParam("new_password") String newPassword,
                                 @RequestParam("current_password") String currentPassword,
                                 @RequestParam("user_id") long userId) {

        User userToUpdate = this.userService.findById(userId);
        if(this.userService.isPasswordsEqual(this.passwordEncoder.encode(currentPassword), userToUpdate.getPassword())){
            newPassword = passwordEncoder.encode(newPassword);
            userToUpdate.setPassword(newPassword);
            return "redirect:/login";
        }


        return "redirect:/my_account";
    }

}
