package crud.controllers;

import crud.model.Role;
import crud.model.User;
import crud.services.RoleService;
import crud.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/adminPage")
    public String adminPage(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return "redirect:/adminPage";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/admin/listUser")
    public String listUser(ModelMap model, Authentication authentication){
        if (authentication.isAuthenticated()) {
            List<User> users = userService.getUsers();
            model.addAttribute("users", users);
            return "adminUser";
        }else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/admin/listRole")
    public String listRole(ModelMap model, Authentication authentication){
        if (authentication.isAuthenticated()) {
            List<User> users = userService.getUsers();
            model.addAttribute("users", users);
            return "adminRole";
        }else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/admin/editUser")
    public String editUser(ModelMap model, @RequestParam long id) {
        User editUser = userService.getUser(id);
        model.addAttribute("user", editUser);
        return "editUser";
    }

    @PostMapping(value = "/admin/editUser")
    public String editUserSubmit(@ModelAttribute User user) {
        userService.editUser(user);
        return "redirect:/admin/listUser";
    }

    @GetMapping(value = "/admin/removeUser")
    public String removeUser(@RequestParam int id) {
        userService.removeUser(id);
        return "redirect:/admin/listUser";
    }

    @GetMapping(value = "/admin/editRole")
    public String editRole(ModelMap model, @RequestParam long id) {
        Role role = roleService.getRole(id);
        model.addAttribute("role", role);
        return "editRole";
    }

    @PostMapping(value = "/admin/editRole")
    public String editRoleSubmit(@ModelAttribute Role role) {
        roleService.editRole(role);
        return "redirect:/admin/listRole";
    }

    @GetMapping(value = "/admin/removeRole")
    public String removeRole(@RequestParam int id) {
        //TODO replace role service method deleteRole to remove by role id
        Role role = roleService.getRole(id);
        roleService.deleteRole(role);
        return "redirect:/admin/listRole";
    }
}
