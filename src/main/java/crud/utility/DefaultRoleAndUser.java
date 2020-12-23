package crud.utility;

import crud.model.Role;
import crud.model.User;
import crud.services.RoleService;
import crud.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import java.util.Arrays;

@Component
public class DefaultRoleAndUser {
    private RoleService roleService;
    private UserService userService;

    private String roleAdmin = "Administrators";
    private String roleUser = "User";
    private String roleAnon = "Anonymous";

    private String adminEmail = "Admin@crud.ru";
    private String password = "admin";


    private DefaultRoleAndUser(@Qualifier(value = "roleServiceImpl") RoleService roleService,
                               @Qualifier(value = "userServiceImpl") UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    private void addDefaultRoleAndUser() {
        Role adminRole;
        Role userRole;
        Role anonRole;
        User admin;

        try {
            adminRole = roleService.getRoleByName(roleAdmin);
        } catch (NoResultException nre) {
            adminRole = new Role(this.roleAdmin);
            adminRole.setRemoved(false);
            roleService.addRole(adminRole);
        }

        try {
            userRole = roleService.getRoleByName(roleUser);
        } catch (NoResultException nre) {
            userRole = new Role(roleUser);
            userRole.setRemoved(false);
            roleService.addRole(userRole);
        }

        try {
            anonRole = roleService.getRoleByName(roleAnon);
        } catch (NoResultException nre) {
            anonRole = new Role(roleAnon);
            anonRole.setRemoved(false);
            roleService.addRole(anonRole);
        }

        try {
            admin = userService.getUserByEmail(adminEmail);
        } catch (NoResultException nre) {
            admin = new User("Admin", "", adminEmail, password, Arrays.asList(adminRole, userRole, anonRole));
            userService.saveUser(admin);
        }
    }
}
