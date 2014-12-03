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
        if (isOverRateLimit())
        {
            requestContext.abortWith(Response.status(429).entity("Too Many Requests").build());
        }
    }

    // testable method to do the rate limit check
    private boolean isOverRateLimit() {
        String requestIdentifier = generateRequestIdentifier();
        if (requestIdentifier == null) {
            return false;
        }
        Jedis jedis = jedisPool.getResource();

        // build the parameters of redis job
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(requestIdentifier);
        ArrayList<String> params = new ArrayList<String>();
        params.add("[[1, 10], [3, 200], [36, 3000]]");
        Long unixTime = System.currentTimeMillis() / 1000L;
        params.add(unixTime.toString());
        params.add("1");

        // run the script from cache, this is the sha1 hash of SlidingWindowRateLimit.lua
        long result = (long)jedis.evalsha("08b03d80e639c5b33de9df638ca4fecbd727b1d0", keys, params);

        jedisPool.returnResource(jedis);
        if (result != 0) {
            return true;
        } else {
            return false;
        }

    }

    // generate a request identifier, either the IP address, or the user, if logged in
    private String generateRequestIdentifier() {
        if (request.getRemoteUser() != null) {
            return "user:"+ request.getRemoteUser();
        } else {
            return "ip:"+ request.getRemoteAddr();
        }
    }

}
