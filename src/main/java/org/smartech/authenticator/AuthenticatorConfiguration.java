package org.smartech.authenticator;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by cneto on 05-11-2014.
 */
public class AuthenticatorConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    public DataSourceFactory database = new DataSourceFactory();

    @Valid
    @JsonProperty
    @NotEmpty
    public String bearerRealm;

}
