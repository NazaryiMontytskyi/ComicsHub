package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Security.Role;
import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.ComicsService;
import org.comicshub.comichub.Services.CountriesService;
import org.comicshub.comichub.Services.GenresService;
import org.comicshub.comichub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    UserService userService;
    ComicsService comicsService;
    GenresService genresService;
    CountriesService countriesService;

    @Autowired
    public AdminController(UserService userService, ComicsService comicsService,
                           GenresService genresService, CountriesService countriesService) {
        this.userService = userService;
        this.comicsService = comicsService;
        this.genresService = genresService;
        this.countriesService = countriesService;

        if(!this.userService.userExistsByUsername("admin")){
            this.createBasicAdmin();
        }
    }

    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        boolean isAdmin = true;
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("comics", comicsService.index());
        model.addAttribute("genres", genresService.findAll());
        model.addAttribute("countries", countriesService.findAll());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", this.userService.getUserByPrincipal(principal));
        return "admin/index";
    }

    @PatchMapping("/deactivate")
    public String deactivate(@RequestParam("user_id") long userId){
        User userToDeactivate = this.userService.findById(userId);
        userToDeactivate.setActive(false);
        this.userService.updateUser(userToDeactivate);
        return "redirect:/admin/index";
    }

    @PatchMapping("/activate")
    public String activate(@RequestParam("user_id") long userId){
        User userToActivate = this.userService.findById(userId);
        userToActivate.setActive(true);
        this.userService.updateUser(userToActivate);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("user_id") long userId){
        this.userService.deleteUser(userId);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/delete_comic")
    public String deleteComic(@RequestParam("comic_id") long comicId){
        this.comicsService.deleteComic(comicId);
        return "redirect:/admin/index";
    }

    private void createBasicAdmin(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin123");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_ADMIN);
        user.setRoles(roles);
        this.userService.createUser(user);
    }

}
