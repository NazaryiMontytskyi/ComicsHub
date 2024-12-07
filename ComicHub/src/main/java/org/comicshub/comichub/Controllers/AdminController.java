package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Security.Role;
import org.comicshub.comichub.Security.User;
import org.comicshub.comichub.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
        if(!this.userService.userExistsByUsername("admin")){
            this.createBasicAdmin();
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("users", userService.findAllUsers());
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
