package org.smartech.authenticator;

import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.smartech.authenticator.auth.OAuth2Authenticator;
import org.smartech.authenticator.core.AccessToken;
import org.smartech.authenticator.core.TokenRole;
import org.smartech.authenticator.core.User;
import org.smartech.authenticator.core.UserRole;
import org.smartech.authenticator.db.AccessTokenDAO;
import org.smartech.authenticator.db.UserDAO;
import org.smartech.authenticator.db.UserRoleDAO;
import org.smartech.authenticator.db.impl.AccessTokenDAOImpl;
import org.smartech.authenticator.db.impl.UserDAOImpl;
import org.smartech.authenticator.db.impl.UserRoleDAOImpl;
import org.smartech.authenticator.health.AuthenticatorHealthCheck;
import org.smartech.authenticator.resources.AuthenticatorResource;
import org.smartech.authenticator.resources.AuthenticatorTokenResource;
import io.dropwizard.Application;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cneto on 05-11-2014.
 */
public class AuthenticatorApplication extends Application<AuthenticatorConfiguration> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticatorApplication.class);

    private final HibernateBundle<AuthenticatorConfiguration> hibernateBundle =
            new HibernateBundle<AuthenticatorConfiguration>(User.class, AccessToken.class, TokenRole.class, UserRole.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(AuthenticatorConfiguration configuration) {
                    return configuration.database;
                }
            };


    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println("Start Ágora Authenticator App with default configuration ...");
            args = new String[2];
            args[0] = "server";
            args[1] = Resources.getResource("agora-authenticator.yml").toURI().getPath();
        }

        new AuthenticatorApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AuthenticatorConfiguration> configuration) {
        configuration.addBundle(hibernateBundle);
    }

    @Override
    public void run(AuthenticatorConfiguration configuration, Environment environment) throws Exception {

        final Injector injector = createInjector(configuration);

        LOGGER.info("Register the Authentication Resources...");
        environment.jersey().register(injector.getInstance(AuthenticatorResource.class));
        environment.jersey().register(injector.getInstance(AuthenticatorTokenResource.class));

        environment.jersey().register(new OAuthProvider<>(
                injector.getInstance(OAuth2Authenticator.class), configuration.bearerRealm));

        LOGGER.info("Register the Authentication HealthCheck ...");
        environment.healthChecks().register(environment.getName(), injector.getInstance(AuthenticatorHealthCheck.class));


    }

    private Injector createInjector(final AuthenticatorConfiguration configuration){

        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

                // @Inject SessionFactory for DAO's
                bind(SessionFactory.class).toInstance(hibernateBundle.getSessionFactory());

                // @Inject DAO services
                bind(UserDAO.class).to(UserDAOImpl.class);
                bind(UserRoleDAO.class).to(UserRoleDAOImpl.class);
                bind(AccessTokenDAO.class).to(AccessTokenDAOImpl.class);

                // @Inject list of allowed grant types
                //bind(new TypeLiteral<ImmutableList<String>>() {}).toInstance(configuration.allowedGrantTypes);

            }
        });

    }

}
