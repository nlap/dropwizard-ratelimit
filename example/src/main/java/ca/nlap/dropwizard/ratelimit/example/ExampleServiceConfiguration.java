package ca.nlap.dropwizard.ratelimit.example;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import ca.nlap.dropwizard.ratelimit.RateLimitConfiguration;
import io.dropwizard.Configuration;

public class ExampleServiceConfiguration extends Configuration {
	@NotNull
	@JsonProperty
	private RateLimitConfiguration jedis;

	public RateLimitConfiguration getJedis() {
		return jedis;
	}

	public void setJedis(RateLimitConfiguration jedis) {
		this.jedis = jedis;
	}
}
