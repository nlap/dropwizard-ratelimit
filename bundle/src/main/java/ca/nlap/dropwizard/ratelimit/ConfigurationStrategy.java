package ca.nlap.dropwizard.ratelimit;

import io.dropwizard.Configuration;

public interface ConfigurationStrategy<T extends Configuration> {
    RateLimitConfiguration getRateLimitConfiguration(T configuration);
}
