package org.smartech.authenticator.db.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.smartech.authenticator.core.UserRole;
import org.smartech.authenticator.db.UserRoleDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;
import com.google.common.base.Optional;

/**
 * Created by jpedro on 18-11-2014.
 */
@Singleton
public class UserRoleDAOImpl extends AbstractDAO<UserRole> implements UserRoleDAO {
    private final static String FIND_BY_USERNAME_AND_PASSWORD = "com.sinfic.agora.authentication.core.domain.UserRole.findByUser";

    @Inject
    public UserRoleDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<List<UserRole>> getRolesByUser(Long userId) {
        Query query = namedQuery(FIND_BY_USERNAME_AND_PASSWORD);
        query.setParameter("userId", userId);

        final List<UserRole> userRoles = list(query);

        return Optional.fromNullable(userRoles);
    }
}
