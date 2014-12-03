package ca.nlap.dropwizard.ratelimit.example;

import ca.nlap.dropwizard.ratelimit.RateLimitConfiguration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ExampleServiceConfigurationTest {
    private final ExampleServiceConfiguration configuration = new ExampleServiceConfiguration();

    @Test
    public void hasRateLimitConfiguration() throws Exception {
        configuration.setRateLimit(mock(RateLimitConfiguration.class));
        assertThat(configuration.getRateLimit()).isNotNull();
    }
}