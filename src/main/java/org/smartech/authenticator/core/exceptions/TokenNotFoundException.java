package org.smartech.authenticator.core.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jpedro on 18-11-2014.
 */
public class TokenNotFoundException extends WebApplicationException {
    public TokenNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity(message)
                .type(MediaType.TEXT_PLAIN)
                .build());
    }
}
