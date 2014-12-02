package ca.nlap.dropwizard.ratelimit;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * This dropwizard bundle adds support for rate limiting.
 * Use it by annotating the resources you want to rate limit with @RateLimited.
 */
public abstract class RateLimitBundle<T extends Configuration> implements ConfiguredBundle<T>, ConfigurationStrategy<T> {
	private JedisPoolConfig jedisPoolConfig;
	private JedisPool jedisPool;

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		RateLimitConfiguration conf = getRateLimitConfiguration(configuration);
		jedisPoolConfig = conf.poolConfig;
		jedisPool = new JedisPool(jedisPoolConfig, conf.getRedisHost(), conf.getRedisPort());

		environment.healthChecks().register("redis", new RedisHealthCheck(jedisPool));

		RateLimitFilter rateLimitFilter = new RateLimitFilter(jedisPool);
		environment.jersey().register(rateLimitFilter);
	}

	@Override
	public void initialize(Bootstrap<?> bootstrap) {
	}
}
