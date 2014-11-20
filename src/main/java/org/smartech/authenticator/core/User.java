package org.smartech.authenticator.core;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by cneto on 30-10-2014.
 */
@Entity
@Table(name = "agora_colaborador")
@NamedQueries({
        @NamedQuery(name = "com.sinfic.agora.authentication.core.domain.User.findByUsernameAndPassword",
                query = "select c from User c where c.active = 'S' and c.username = :username and c.password = :password"
        )
})
public class User extends SimplePersistentObject {

    @Column(name = "login")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "activo")
    public String active;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;
}
