package ca.nlap.dropwizard.ratelimit;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RateLimitConfigurationTest {
    private final RateLimitConfiguration configuration = new RateLimitConfiguration();

    @Test
    public void hasJedisPoolConfig() throws Exception {
        assertThat(configuration.getPoolConfig()).isNotNull();
    }
}