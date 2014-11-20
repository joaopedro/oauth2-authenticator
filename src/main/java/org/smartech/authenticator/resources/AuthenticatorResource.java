package org.smartech.authenticator.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import org.smartech.authenticator.core.AccessToken;
import org.smartech.authenticator.db.AccessTokenDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by cneto on 30-10-2014.
 */
@Path("/authenticate/auth")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AuthenticatorResource {
    @Inject
    private AccessTokenDAO accessTokenDAO;

    private Logger LOGGER = LoggerFactory.getLogger(AuthenticatorResource.class);

    @GET
    @Timed
    @UnitOfWork
    public AccessToken authenticate(@Auth AccessToken token) {
        token.setLastAccessUTC(new DateTime());
        accessTokenDAO.save(token);
        return token;
    }

}
