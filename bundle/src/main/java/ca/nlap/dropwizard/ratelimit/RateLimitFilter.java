package ca.nlap.dropwizard.ratelimit;

import redis.clients.jedis.JedisPool;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class RateLimitFilter implements ContainerRequestFilter {

    private JedisPool jedisPool;

    public RateLimitFilter(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        // check if HTTPS
        if (!requestContext.getSecurityContext().isSecure())
        {
            // if not, abort the request
            requestContext.abortWith(Response.status(Response.Status.fromStatusCode(429))
                    .entity("Too Many Requests")
                    .build());
        }
    }

}
