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
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping(value = "/admin/users")
    public String listUser(ModelMap model, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            List<User> users = userService.getUsers();
            model.addAttribute("users", users);
            return "users";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/admin/editUser")
    public String editUser(ModelMap model, @RequestParam long id) {
        User editUser = userService.getUser(id);
        model.addAttribute("user", editUser);
        return "edituser";
    }

    @PostMapping(value = "/admin/editUser")
    public String editUserSubmit(@ModelAttribute User user,
                                 @RequestParam(name = "roleAdmin", defaultValue = "false") boolean isAdmin,
                                 @RequestParam(name = "roleUser", defaultValue = "false") boolean isUser) {

        Role role_administrators = roleService.getRoleByName("ROLE_Administrators");
        Role role_user = roleService.getRoleByName("ROLE_User");
        user.getRoles().clear();

        if (isAdmin && !user.getRoles().contains(role_administrators)){
            user.getRoles().add(role_administrators);
        }else {
            user.getRoles().remove(role_administrators);
        }

        if (isUser && !user.getRoles().contains(role_user)) {
            user.getRoles().add(role_user);
        } else {
            user.getRoles().remove(role_user);
        }

        userService.editUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/removeUser")
    public String removeUser(@RequestParam int id) {
        userService.removeUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/newUser")
    public String newUserForm(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "newUser";
    }

    @PostMapping("/admin/newUser")
    public String newUser(@ModelAttribute User user,
                          @RequestParam(name = "roleAdmin", defaultValue = "false") boolean isAdmin,
                          @RequestParam(name = "roleUser", defaultValue = "false") boolean isUser) {

        userService.saveUser(user);
        if (isAdmin) user.getRoles().add(roleService.getRoleByName("ROLE_Administrators"));
        if (isUser) user.getRoles().add(roleService.getRoleByName("ROLE_User"));
        userService.editUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/roles")
    public String listUsers(ModelMap map) {
        Set<Role> roles = roleService.getRoles();
        map.addAttribute("roles", roles);
        return "roles";
    }
}
