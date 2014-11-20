package org.smartech.authenticator.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jpedro on 18-11-2014.
 */
@Entity
@Table(name = "agora_colorganicafuncao")
@NamedQueries({
        @NamedQuery(name = "com.sinfic.agora.authentication.core.domain.UserRole.findByUser",
                query = "select r from UserRole r where r.id = :userId"
        )
})
public class UserRole implements Serializable {
    @Id @Column(name = "id") public long id;
    @Id public long idOrganica;
    @Id public long idFuncao;
}
