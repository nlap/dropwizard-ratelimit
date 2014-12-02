package ca.nlap.dropwizard.ratelimit.example;

import ca.nlap.dropwizard.ratelimit.RateLimitBundle;
import ca.nlap.dropwizard.ratelimit.RateLimitConfiguration;
import ca.nlap.dropwizard.ratelimit.example.resources.ExampleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleService extends Application<ExampleServiceConfiguration> {
	private RateLimitBundle<ExampleServiceConfiguration> rateLimitBundle = new RateLimitBundle<ExampleServiceConfiguration>() {
		@Override
		public RateLimitConfiguration getJedisConfiguration(
				ExampleServiceConfiguration configuration) {
			return configuration.getJedis();
		}
	};

	@Override
	public String getName() {
		return "dropwizard-ratelimit-example";
	}
	
	@Override
	public void initialize(Bootstrap<ExampleServiceConfiguration> bootstrap) {
		bootstrap.addBundle(rateLimitBundle);
	}

	@Override
	public void run(ExampleServiceConfiguration configuration,
			Environment environment) throws Exception {
		environment.jersey().register(ExampleResource.class);
	}
	
	public static void main(String[] args) throws Exception {
		new ExampleService().run(args);
	}
}
