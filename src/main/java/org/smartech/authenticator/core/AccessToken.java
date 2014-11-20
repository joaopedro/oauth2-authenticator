package org.smartech.authenticator.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by cneto on 30-10-2014.
 */
@Entity
@Table(name = "agora_acesstoken")
@NamedQueries({
        @NamedQuery(name = "com.sinfic.agora.authentication.core.AccessToken.findAccessTokenByToken",
                query = "select at from AccessToken at where at.access_token = :access_token"
        ),
        @NamedQuery(name = "com.sinfic.agora.authentication.core.AccessToken.findAccessTokenByRefreshToken",
                query = "select at from AccessToken at where at.refresh_token= :refresh_token"
        ),
        @NamedQuery(name = "com.sinfic.agora.authentication.core.AccessToken.findAccessTokenByUser",
                query = "select at from AccessToken at where at.user_id = :userId and at.clientId = :clientId"
        )
})
public class AccessToken extends SimplePersistentObject{

    @Transient private final int TIMEOUT_SECONDS = 1800;

    private UUID access_token;
    private UUID refresh_token;
    private final String token_type="bearer";
    private final String issuer="http://authentication.agora.com";
    private long user_id;
    private String username;
    private String clientId;
    @ElementCollection
    @CollectionTable(name="agora_acesstoken_role", joinColumns=@JoinColumn(name="token_id"))
    @Column(name="role")
    private List<String> roles = new ArrayList<>();

    @NotNull
    @JsonIgnore
    private DateTime lastAccessUTC;
    public AccessToken(){};
    public AccessToken(UUID access_token,UUID refresh_token, Long user_id, String username, String clientId,
                      DateTime lastAccessUTC) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.user_id = user_id;
        this.username = username;
        this.clientId = clientId;
        this.lastAccessUTC = lastAccessUTC;
    }

    public int getExpires_in(){
        DateTime now = DateTime.now();
        DateTime expirationDate = lastAccessUTC.plusSeconds(TIMEOUT_SECONDS);
        return Seconds.secondsBetween(lastAccessUTC, expirationDate).getSeconds();
    }

    public UUID getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getIssuer() {
        return issuer;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setLastAccessUTC(DateTime lastAccessUTC) {
        this.lastAccessUTC = lastAccessUTC;
    }

    public DateTime getLastAccessUTC() {
        return lastAccessUTC;
    }

    public void setAccess_token(UUID access_token) {
        this.access_token = access_token;
    }

    public UUID getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(UUID refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getRoles() {
        return roles;
    }
}
