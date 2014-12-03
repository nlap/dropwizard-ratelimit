package ca.nlap.dropwizard.ratelimit;

import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.Configuration;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RateLimitBundleTest {

    private final Configuration configuration = mock(Configuration.class);
    private final HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
    private final LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
    private final JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);
    private final Environment environment = mock(Environment.class);
    private final JedisPool jedisPool = mock(JedisPool.class);
    private final Jedis jedis = mock(Jedis.class);
    private final RateLimitBundle<Configuration> bundle = new RateLimitBundle<Configuration>() {
        @Override
        public RateLimitConfiguration getRateLimitConfiguration(Configuration configuration) {
            RateLimitConfiguration conf = new RateLimitConfiguration();
            conf.setRedisHost("localhost");
            conf.setRedisPort(6379);
            return conf;
        }
    };

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        when(environment.healthChecks()).thenReturn(healthChecks);
        when(environment.jersey()).thenReturn(jerseyEnvironment);
        when(environment.lifecycle()).thenReturn(lifecycle);
        when(jedisPool.getResource()).thenReturn(jedis);
    }

    @Test
    public void registersRateLimitFilter() throws Exception {
        bundle.run(configuration, environment, jedisPool);
        verify(jerseyEnvironment).register(any(RateLimitFilter.class));
    }

    @Test
    public void loadsRedisLuaScript() throws Exception {
        bundle.run(configuration, environment, jedisPool);
        final ArgumentCaptor<String> captor =
                ArgumentCaptor.forClass(String.class);
        verify(jedis).scriptLoad(captor.capture());
        assertThat(sha1(captor.getValue())).isEqualTo("08b03d80e639c5b33de9df638ca4fecbd727b1d0");
    }

    @Test
    public void registersARedisHealthCheck() throws Exception {
        bundle.run(configuration, environment, jedisPool);
        verify(healthChecks).register(eq("redis"), any(RedisHealthCheck.class));
    }

    // message digest methods for verifying lua script loading
    private static String sha1(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }
    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
