package org.smartech.authenticator.db.impl;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import org.smartech.authenticator.core.AccessToken;
import org.smartech.authenticator.db.AccessTokenDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.UUID;

/**
 * Created by cneto on 03-11-2014.
 */
public class AccessTokenDAOImpl extends AbstractDAO<AccessToken> implements AccessTokenDAO {

    private final static String FIND_ACCESS_TOKEN_BY_TOKEN = "com.sinfic.agora.authentication.core.AccessToken.findAccessTokenByToken";
    private final static String FIND_ACCESS_TOKEN_BY_REFRESH_TOKEN = "com.sinfic.agora.authentication.core.AccessToken.findAccessTokenByRefreshToken";
    private final static String FIND_ACCESS_TOKEN_BY_USER = "com.sinfic.agora.authentication.core.AccessToken.findAccessTokenByUser";

    @Inject
    public AccessTokenDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<AccessToken> findAccessTokenById(UUID token) {
        Query query = namedQuery(FIND_ACCESS_TOKEN_BY_TOKEN);
        query.setParameter("access_token", token);
        final AccessToken accessToken = uniqueResult(query);
        return Optional.fromNullable(accessToken);
    }

    @Override
    public void save(AccessToken accessToken) {
        persist(accessToken);
    }

    @Override
    public Optional<AccessToken> findByUserAndClientId(long userId, String clientId) {
        Query query = namedQuery(FIND_ACCESS_TOKEN_BY_USER);
        query.setParameter("userId", userId);
        query.setParameter("clientId", clientId);
        final AccessToken accessToken = uniqueResult(query);
        return Optional.fromNullable(accessToken);
    }

    @Override
    public Optional<AccessToken> findByRefreshToken(UUID refreshToken) {
        Query query = namedQuery(FIND_ACCESS_TOKEN_BY_REFRESH_TOKEN);
        query.setParameter("refresh_token", refreshToken);
        final AccessToken accessToken = uniqueResult(query);
        return Optional.fromNullable(accessToken);
    }

}
