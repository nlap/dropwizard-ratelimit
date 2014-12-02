package ca.nlap.dropwizard.ratelimit.example;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import ca.nlap.dropwizard.ratelimit.RateLimitConfiguration;
import io.dropwizard.Configuration;

public class ExampleServiceConfiguration extends Configuration {
	@NotNull
	@JsonProperty
	private RateLimitConfiguration rateLimit;

	public RateLimitConfiguration getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(RateLimitConfiguration rateLimit) {
		this.rateLimit = rateLimit;
	}
}
