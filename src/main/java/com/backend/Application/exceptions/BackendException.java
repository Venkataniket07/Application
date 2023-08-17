package com.backend.Application.exceptions;

import com.backend.Application.util.ErrorResponse;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackendException extends WebApplicationException {
    private final int status;
    private final String message;

    public BackendException(int status, String message) {
        super(Response.status(status)
                .entity(new ErrorResponse(status, message))
                .type(MediaType.APPLICATION_JSON)
                .build());
        this.status = status;
        this.message = message;
    }
}