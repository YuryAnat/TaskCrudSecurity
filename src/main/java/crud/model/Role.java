package crud.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "ROLE_GEN")
    @OrderBy
    private long id;
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getSimpleRoleName(){
        return roleName.replace("ROLE_", "");
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
