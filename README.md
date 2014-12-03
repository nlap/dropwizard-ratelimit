***Work in progress***

[![Build Status](https://travis-ci.org/nlap/dropwizard-ratelimit.svg)](https://travis-ci.org/nlap/dropwizard-ratelimit) [![Coverage Status](https://img.shields.io/coveralls/nlap/dropwizard-ratelimit.svg)](https://coveralls.io/r/nlap/dropwizard-ratelimit)

dropwizard-ratelimit is a [dropwizard](http://dropwizard.io/) bundle that allows [redis](http://redis.io)-backed rate limiting for your resources. It performs [sliding-window rate limiting](http://www.binpress.com/tutorial/introduction-to-rate-limiting-with-redis-part-2/166).

Requires Dropwizard 0.8.0+

# Usage
Add the following dependency to your `pom.xml`

    <dependency>
      <groupId>ca.nlap.dropwizard-ratelimit</groupId>
      <artifactId>bundle</artifactId>
      <version>0.0.1</version>
    </dependency>

Now add the bundle:

	private RateLimitBundle<ExampleServiceConfiguration> rateLimitBundle = new RateLimitBundle<ExampleServiceConfiguration>() {
		@Override
		public RateLimitConfiguration getRateLimitConfiguration(
				ExampleServiceConfiguration configuration) {
			return configuration.getRateLimit();
		}
	};
	
	@Override
	public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
		bootstrap.addBundle(rateLimitBundle);
	}

Finally, annotate any resources you want to protect with `@RateLimited`:

	import ca.nlap.dropwizard.ratelimit.RateLimited;
	...

	@Path("/")
	public class ExampleResource {
	
		@GET
		@RateLimited
		public Response getResource() {
			return new Response.ok().build();
		}

	}
	
# Configuration
Configure as per the example conf.yaml. The properties are:

* *redisHost*: Redis hostname
* *redisPort*: Redis port

# Example
The module `example` contains an API with a single resource that simply returns 200 OK. This is rate limited to 3 requests per second.


Start and configure redis. Run `run-example.sh` to start the service and run the test. Multiple requests will be sent, demonstrating the rate limiting:
	
	200 OK
	200 OK
	200 OK
	429 Too Many Requests
	
# Open Work
The following is not yet available. Contributions are welcomed.

* df
* df

# Contributions
Adapts code from https://github.com/anastasop/dropwizard-jedis  
Rate limitng lua script from https://gist.github.com/josiahcarlson/80584b49da41549a7d5c  
