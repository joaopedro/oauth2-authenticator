package org.smartech.authenticator.db.impl;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.smartech.authenticator.core.User;
import org.smartech.authenticator.db.UserDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 * Created by cneto on 30-10-2014.
 */
@Singleton
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    private final static String FIND_BY_USERNAME_AND_PASSWORD = "com.sinfic.agora.authentication.core.domain.User.findByUsernameAndPassword";

    @Inject
    public UserDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {

        Query query = namedQuery(FIND_BY_USERNAME_AND_PASSWORD);
        query.setParameter("username", username);
        query.setParameter("password", password);

        final User colaborador = uniqueResult(query);

        return Optional.fromNullable(colaborador);
    }
}
