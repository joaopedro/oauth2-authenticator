package org.smartech.authenticator.auth;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import org.smartech.authenticator.core.AccessToken;
import org.smartech.authenticator.db.AccessTokenDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

/**
 * Created by cneto on 04-11-2014.
 */
public class OAuth2Authenticator implements Authenticator<String, AccessToken> {

    public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 30;

    @Inject
    private AccessTokenDAO accessTokenDAO;

    @Override
    public Optional<AccessToken> authenticate(String token) throws AuthenticationException {

        // Check input, must be a valid UUID
        UUID uuid;
        try {
            uuid = UUID.fromString(token);
        } catch (IllegalArgumentException e) {
            return Optional.absent();
        }

        // Get the access token from the database
        Optional<AccessToken> accessToken = accessTokenDAO.findAccessTokenById(uuid);
        if (accessToken == null || !accessToken.isPresent()) {
            return Optional.absent();
        }

        // get access token instance
        AccessToken aToken = accessToken.get();

        // Check if the last access time is not too far in the past (the access token is expired)
        Period period = new Period(aToken.getLastAccessUTC(), new DateTime());
        if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN) {
            return Optional.absent();
        }

        // Update the access time for the token
        aToken.setLastAccessUTC(new DateTime());
        accessTokenDAO.save(aToken);

        // Return the user's id for processing
        return Optional.of(aToken);

    }

}
