package org.smartech.authenticator.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import org.smartech.authenticator.core.AccessToken;
import org.smartech.authenticator.core.User;
import org.smartech.authenticator.core.UserRole;
import org.smartech.authenticator.core.exceptions.TokenNotFoundException;
import org.smartech.authenticator.core.exceptions.UnauthorizedException;
import org.smartech.authenticator.db.AccessTokenDAO;
import org.smartech.authenticator.db.UserDAO;
import org.smartech.authenticator.db.UserRoleDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

/**
 * Created by cneto on 30-10-2014.
 */
@Path("/authenticate")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AuthenticatorTokenResource {

    private Logger LOGGER = LoggerFactory.getLogger(AuthenticatorTokenResource.class);

    @Inject
    private UserDAO userDAO;

    @Inject
    private UserRoleDAO userRoleDAO;

    @Inject
    private AccessTokenDAO accessTokenDAO;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Timed
    @UnitOfWork
    @Path("/token")
    public AccessToken generateToken(@FormParam("username") String username, @FormParam("password") String password,
                                     @FormParam("clientid") String clientId) {

        LOGGER.info("create access token for " + username);

        // Try to find a user with the supplied credentials.
        Optional<User> user = userDAO.findByUsernameAndPassword(username, password);
        if (user == null || !user.isPresent()) {
            String error = "Credentials are required to access this resource.";
            LOGGER.error(error);
            throw new UnauthorizedException(error);
        }

        AccessToken aToken;
        final Optional<AccessToken> accessToken = accessTokenDAO.findByUserAndClientId(user.get().id, clientId);
        if (accessToken == null || !accessToken.isPresent()) {
            // User does not have token yet, generate a new token.
            aToken = new AccessToken(UUID.randomUUID(),UUID.randomUUID(), user.get().id, user.get().username, clientId, new DateTime());

        } else {
            // User already have token, generate a new token and update
            aToken = accessToken.get();
            aToken.setAccess_token(UUID.randomUUID());
            aToken.setRefresh_token(UUID.randomUUID());
            aToken.setLastAccessUTC(new DateTime());
            aToken.getRoles().clear();
        }

        fillTokenRoles(user, aToken);

        accessTokenDAO.save(aToken);

        return aToken;

    }

    private void fillTokenRoles(Optional<User> user, AccessToken aToken) {
        Optional<List<UserRole>> userRoles = userRoleDAO.getRolesByUser(user.get().id);

        if(userRoles.isPresent()){
            for (UserRole userRole : userRoles.get()) {
                String role = "O"+userRole.idOrganica+"_F"+userRole.idFuncao;
                aToken.getRoles().add(role);
            }
        }
        aToken.getRoles().add("ROLE_INTERNO");
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Timed
    @UnitOfWork
    @Path("/refresh")
    public AccessToken generateToken(@Auth @FormParam("token") String refreshToken) {

        AccessToken aToken=null;
        final Optional<AccessToken> accessToken = accessTokenDAO.findByRefreshToken(UUID.fromString(refreshToken));
        if (accessToken.isPresent()) {
            aToken = accessToken.get();
            aToken.setRefresh_token(UUID.randomUUID());
            aToken.setLastAccessUTC(new DateTime());
            accessTokenDAO.save(aToken);
        }else{
            String info = "Token with refresh "+refreshToken+" not found!";
            LOGGER.error(info);
            throw new TokenNotFoundException(info);
        }


        return aToken;
    }


}
