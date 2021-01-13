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
import java.util.HashSet;
import java.util.Set;

@Component
public class DefaultRoleAndUser {
    private RoleService roleService;
    private UserService userService;

    private String roleAdmin = "ROLE_Administrators";
    private String roleUser = "ROLE_User";

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
        User admin;

        try {
            adminRole = roleService.getRoleByName(roleAdmin);
        } catch (NoResultException nre) {
            adminRole = new Role(roleAdmin);
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
            admin = userService.getUserByEmail(adminEmail);
        } catch (NoResultException nre) {
            Set<Role> roles = new HashSet<>(Arrays.asList(adminRole, userRole));
            admin = new User("Admin", "", adminEmail, password, roles);
            admin.setEnabled(true);
            userService.saveUser(admin);
        }
    }
}
