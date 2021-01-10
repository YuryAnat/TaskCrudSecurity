package crud.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "USER_GEN")
    @OrderBy
    private long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String lastName;
    private String password;
    @Transient
    private String confirmPassword;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<Role> roles;
    private boolean isEnabled;
    private boolean isCredentialsNonExpired;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;

    public User(String name, String lastName, String email, String password, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isEnabled = false;
        this.isCredentialsNonExpired = true;
        this.isAccountNonLocked = true;
        this.isAccountNonExpired = true;
    }

    public Set<String> getRolesNames(){
        if (null != roles){
            return roles.stream().map(Role::getRoleName).map(r -> r.replace( "ROLE_", "")).collect(Collectors.toSet());
        }else {
            return Collections.emptySet();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
