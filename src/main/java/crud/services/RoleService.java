package crud.services;

import crud.model.Role;

import java.util.Set;

public interface RoleService {
    void addRole(Role role);

    Role getRole(long id);

    void deleteRole(Role role);

    void editRole(Role role);

    Role getRoleByName(String role);

    Set<Role> getRoles();
}
