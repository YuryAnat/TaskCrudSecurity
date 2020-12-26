package crud.controllers;

import crud.model.User;
import crud.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String homePage(Authentication authentication) {
        if (null != authentication && authentication.isAuthenticated()) {
            boolean isAdmin = AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("Administrators");
            if (isAdmin) {
                return "redirect:/admin/adminPage";
            } else {
                return "redirect:/user/userPage";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/userPage")
    public String userPage(ModelMap model, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            model.addAttribute("user", currentUser);
            return "redirect:/userPage";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/registration")
    public String addUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUserSubmit(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
