***Work in progress***

dropwizard-ratelimit is a [dropwizard](http://dropwizard.io/) bundle that allows [redis](http://redis.io)-backed rate limiting for your resources.

Requires Dropwizard 0.8.0+, for JAX-RS 2.0

# Usage
First build the project with `mvn clean install` and add the following dependency to your `pom.xml`

    <dependency>
      <groupId>ca.nlap.dropwizard-ratelimit</groupId>
      <artifactId>bundle</artifactId>
      <version>0.0.1</version>
    </dependency>

Now add the bundle:

	private RateLimitBundle<ExampleConfiguration> rateLimitBundle = new RateLimitBundle<ExampleConfiguration>() {
		@Override
		public JedisConfiguration getJedisConfiguration(
				ExampleConfiguration configuration) {
			return configuration.getJedis();
		}
	};
	
	@Override
	public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
		bootstrap.addBundle(rateLimitBundle);
	}

Finally, annotate any resources you want to protect with `@RateLimited`:

	import ca.nlap.dropwizard.ratelimit.RateLimited;

	@Path("/")
	public class ExampleResource {
	
		@GET
		@RateLimited
		public Response getResource() {
			return new Response.ok().build();
		}

	}

# Example
The module `example` contains an API with a single resource that simply returns 200 OK. This is rate limited to 3 requests per second.


Start and configure redis. Run `run-example.sh` to start the service and run the test. Multiple requests will be sent, demonstrating the rate limiting:
	
	200 OK
	200 OK
	200 OK
	429 Too Many Requests

# Contributing
Adapts code from anastasop/dropwizard-jedis  
Contributions welcome

