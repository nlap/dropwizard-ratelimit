package ca.nlap.dropwizard.ratelimit;

import io.dropwizard.Configuration;

public interface ConfigurationStrategy<T extends Configuration> {
    RateLimitConfiguration getJedisConfiguration(T configuration);
}
