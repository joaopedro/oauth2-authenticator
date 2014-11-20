package org.smartech.authenticator.db;

import org.smartech.authenticator.core.UserRole;

import java.util.List;
import com.google.common.base.Optional;

/**
 * Created by jpedro on 18-11-2014.
 */
public interface UserRoleDAO {
    Optional<List<UserRole>> getRolesByUser(Long userId);
}
