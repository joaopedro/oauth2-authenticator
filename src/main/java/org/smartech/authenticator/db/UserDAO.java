package org.smartech.authenticator.db;

import com.google.common.base.Optional;
import org.smartech.authenticator.core.User;

/**
 * Created by cneto on 30-10-2014.
 */
public interface UserDAO {
    Optional<User> findByUsernameAndPassword(String username, String password);
}
