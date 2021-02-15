package com._98point6.droptoken.exception;

import io.dropwizard.jersey.errors.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 *
 */
public class DropTokenExceptionMapper implements ExceptionMapper<RuntimeException>  {

    private static final Logger logger = LoggerFactory.getLogger(DropTokenExceptionMapper.class);


    public Response toResponse(RuntimeException e) {
        if (exceptionClassToResponseStatusMap.containsKey(e.getClass()))
        {
            int responseStatus = exceptionClassToResponseStatusMap.get(e.getClass());
            logger.info("Mapped exception type {} to responseStatus: {}", e.getClass().getName(), responseStatus);

            ErrorMessage errorMessage = new ErrorMessage(responseStatus, e.getMessage());
            return Response.status(responseStatus).type(MediaType.APPLICATION_JSON_TYPE).entity(errorMessage).build();
        }
        logger.error("Unhandled exception.", e);
        return Response.status(500).build();
    }

    private static final Map<Class, Integer> exceptionClassToResponseStatusMap = new HashMap<Class, Integer>() {{
        put(NoSuchElementException.class, 404);
        put(GoneException.class, 410);
        put(IllegalArgumentException.class, 400);
        put(IllegalStateException.class, 409);
    }};
}
