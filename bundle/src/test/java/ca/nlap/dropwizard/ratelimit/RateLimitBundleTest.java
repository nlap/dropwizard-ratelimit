package ca.nlap.dropwizard.ratelimit;

import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.Configuration;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RateLimitBundleTest {

    private final Configuration configuration = mock(Configuration.class);
    private final HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
    private final LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
    private final JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);
    private final Environment environment = mock(Environment.class);
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
    }

    @Test
    public void registersRateLimitFilter() throws Exception {
        bundle.run(configuration, environment);
        verify(jerseyEnvironment).register(any(RateLimitFilter.class));
    }

    @Test
    public void registersARedisHealthCheck() throws Exception {
        bundle.run(configuration, environment);
        verify(healthChecks).register(eq("redis"), any(RedisHealthCheck.class));
    }

}
