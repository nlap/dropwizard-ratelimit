package ca.nlap.dropwizard.ratelimit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

@RateLimited
public class RateLimitFilter implements ContainerRequestFilter {

    private JedisPool jedisPool;

    @Context
    private HttpServletRequest request;

    public RateLimitFilter(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        // check if client exceeded rate limit
        if (!requestContext.getSecurityContext().isSecure())
        {
            requestContext.abortWith(Response.status(429).entity("Too Many Requests").build());
        }
    }

}
