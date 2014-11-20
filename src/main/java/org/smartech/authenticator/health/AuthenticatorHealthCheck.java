package org.smartech.authenticator.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import org.smartech.authenticator.db.AccessTokenDAO;
import org.smartech.authenticator.db.UserDAO;

/**
 * Created by cneto on 30-10-2014.
 */
public class AuthenticatorHealthCheck extends HealthCheck {

    @Inject
    private AccessTokenDAO accessTokenDAO;

    @Inject
    private UserDAO userDAO;

    @Override
    protected Result check() throws Exception {
        return Result.healthy("Not really checking anything at this time.");
    }

}
