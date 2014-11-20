package org.smartech.authenticator.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by jpedro on 18-11-2014.
 */
@Entity
@Table(name = "agora_acesstoken_role")
public class TokenRole extends SimplePersistentObject{
    @ManyToOne @JoinColumn(name = "token_id")
    @JsonIgnore private AccessToken token;
    private String role;
    public TokenRole(){}
    public TokenRole(AccessToken token, String role){
        this.token = token;
        this.role = role;
    }

    @JsonIgnore
    public AccessToken  getAccess_token() {
        return token;
    }

    public void setAccess_token(AccessToken  access_token) {
        this.token = access_token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
