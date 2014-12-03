package ca.nlap.dropwizard.ratelimit;

import com.codahale.metrics.health.HealthCheck;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class RedisHealthCheckTest {

    private final JedisPool healthyJedisPool = mock(JedisPool.class);
    private final JedisPool unhealthyJedisPool = mock(JedisPool.class);
    private final Jedis healthyJedis = mock(Jedis.class);
    private final Jedis unhealthyJedis = mock(Jedis.class);

    @Before
    public void setUp() throws Exception {
        when(healthyJedisPool.getResource()).thenReturn(healthyJedis);
        when(unhealthyJedisPool.getResource()).thenReturn(unhealthyJedis);
        when(healthyJedis.ping()).thenReturn("PONG");
        when(unhealthyJedis.ping()).thenReturn(null);
    }

    @Test
    public void isHealthyIfRedisResponds() throws Exception {
        RedisHealthCheck healthCheck = new RedisHealthCheck(healthyJedisPool);
        assertThat(healthCheck.execute())
                .isEqualTo(HealthCheck.Result.healthy());
    }
    @Test
    public void isUnhealthyIfRedisDoesNotRespond() throws Exception {
        RedisHealthCheck healthCheck = new RedisHealthCheck(unhealthyJedisPool);
        assertThat(healthCheck.execute().isHealthy())
                .isFalse();
    }

}