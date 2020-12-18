package crud.controllers;

import crud.model.User;
import crud.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping(value = "/edit")
    public String editUser(ModelMap model, @RequestParam long id) {
        User editUser = userService.getUser(id);
        model.addAttribute("user", editUser);
        return "editUser";
    }

    @PostMapping(value = "/edit")
    public String editUserSubmit(@ModelAttribute User user) {
        userService.editUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/add")
    public String addUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping(value = "/add")
    public String addUserSubmit(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/remove")
    public String removeUser(@RequestParam int id) {
        userService.removeUser(id);
        return "redirect:/";
    }
}
