package crud.controllers;

import crud.model.User;
import crud.services.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;

@Controller
public class WebController {
    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String homePage(Authentication authentication) {
        if (null != authentication && authentication.isAuthenticated()) {
            boolean isAdmin = AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("ROLE_Administrators");
            if (isAdmin) {
                return "redirect:/admin/admin";
            } else {
                return "redirect:/user/user";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/user/user")
    public String userPage(ModelMap model, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            User currentUser = userService.getUserByEmail(authentication.getName());
            model.addAttribute("user", currentUser);
            return "user";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/registration")
    public String addUser(ModelMap modelMap, RedirectAttributes redirectAttributes) {
        redirectAttributes.getAttribute("error");
        modelMap.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUserSubmit(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (user.getPassword().equals(user.getConfirmPassword())){
            userService.saveUser(user);
        } else {
            redirectAttributes.addAttribute("error", "Пароли не совпадают!");
            return "redirect:/registration";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
