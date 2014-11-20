package org.smartech.authenticator.core.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * User: cneto
 * Date: 2014/10/30
 */
public class UnauthorizedException extends WebApplicationException {

    public UnauthorizedException(String message) {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(message)
                .type(MediaType.TEXT_PLAIN)
                .build());
    }

}
