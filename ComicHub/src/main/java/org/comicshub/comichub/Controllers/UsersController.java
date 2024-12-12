package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.ImageContent;
import org.comicshub.comichub.Security.Role;
import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.ComicsService;
import org.comicshub.comichub.Services.UserReadService;
import org.comicshub.comichub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class UsersController {

    private final UserService userService;
    private final ComicsService comicsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserService userService, ComicsService comicsService,
                           PasswordEncoder passwordEncoder) {

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
    public String createUser(User user) throws IOException {
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
        boolean isAdmin = false;
        if(this.userService.getUserByPrincipal(principal) != null)
        {
            isAdmin = this.userService.getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN);
        }
        model.addAttribute("user", user);
        model.addAttribute("comics", this.comicsService.findByUserId(user.getId()));
        model.addAttribute("isAdmin", isAdmin);
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
                                 @RequestParam("confirm_password") String confirmPassword,
                                 @RequestParam("user_id") long userId) {

        User userToUpdate = this.userService.findById(userId);
        if(this.passwordEncoder.matches(currentPassword, userToUpdate.getPassword())){
            if(newPassword.equals(confirmPassword)){
                newPassword = passwordEncoder.encode(newPassword);
                userToUpdate.setPassword(newPassword);
                this.userService.updateUser(userToUpdate);
                return "redirect:/login";
            }
        }

        return "redirect:/my_account";
    }

    @GetMapping("/favourites")
    public String favouriteComics(Model model, Principal principal){
        boolean isAdmin = false;
        if(this.userService.getUserByPrincipal(principal) != null)
        {
            isAdmin = this.userService.getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN);
        }
        User userToRead = this.userService.getUserByPrincipal(principal);
        List<Comic> usersComics = this.comicsService.findUserFavourites(userToRead.getId());
        model.addAttribute("users_comics", usersComics);
        model.addAttribute("user", this.userService.getUserByPrincipal(principal));
        model.addAttribute("isAdmin", isAdmin);
        return "user/user-favourite";
    }

    @DeleteMapping("/users/delete")
    public String removeAccount(@RequestParam("user_id") long userId, Principal principal){
        User anyAdmin = this.userService.getAnyAdmin();
        User userWhoDeletes = this.userService.getUserByPrincipal(principal);
        List<Comic> userToDeleteComics = this.comicsService.findByUserId(userId);
        for(Comic comic : userToDeleteComics){
            comic.setUser(anyAdmin);
            this.comicsService.update(comic);
        }
        this.userService.deleteUser(userId);
        if(userWhoDeletes.getRoles().contains(Role.ROLE_ADMIN)){
            return "redirect:/admin/index";
        }
        return "redirect:/comics/index";
    }

}
