package org.smartech.authenticator.db;

import com.google.common.base.Optional;
import org.smartech.authenticator.core.AccessToken;

import java.util.UUID;

/**
 * Created by cneto on 30-10-2014.
 */
public interface AccessTokenDAO {
    Optional<AccessToken> findAccessTokenById(UUID token);
    void save(AccessToken accessToken);

    Optional<AccessToken> findByUserAndClientId(long userId, String clientId);

    Optional<AccessToken> findByRefreshToken(UUID refreshToken);
}
