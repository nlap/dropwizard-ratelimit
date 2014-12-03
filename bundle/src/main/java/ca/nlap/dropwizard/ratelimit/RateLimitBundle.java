package ca.nlap.dropwizard.ratelimit;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URL;

/**
 * This dropwizard bundle adds support for rate limiting.
 * Use it by annotating the resources you want to rate limit with @RateLimited.
 */
public abstract class RateLimitBundle<T extends Configuration> implements ConfiguredBundle<T>, ConfigurationStrategy<T> {
	private JedisPoolConfig jedisPoolConfig;
	private JedisPool jedisPool;

	@Override
	public void run(T configuration, Environment environment) throws Exception {

		environment.lifecycle().manage(new Managed() {
			@Override
			public void start() throws Exception {
			}

			@Override
			public void stop() throws Exception {
				jedisPool.destroy();
			}
		});


		// load redis config, setup redis connection pool
		RateLimitConfiguration conf = getRateLimitConfiguration(configuration);
		jedisPoolConfig = conf.poolConfig;
		jedisPool = new JedisPool(jedisPoolConfig, conf.getRedisHost(), conf.getRedisPort());
		environment.healthChecks().register("redis", new RedisHealthCheck(jedisPool));

		// load redis lua script into script cache
		URL url = Resources.getResource("SlidingWindowRateLimit.lua");
		String slidingWindowRateLimitLua = Resources.toString(url, Charsets.UTF_8);
		Jedis jedis = jedisPool.getResource();
		jedis.scriptLoad(slidingWindowRateLimitLua);
		jedisPool.returnResource(jedis);

		// register the filter
		RateLimitFilter rateLimitFilter = new RateLimitFilter(jedisPool);
		environment.jersey().register(rateLimitFilter);
	}

	@Override
	public void initialize(Bootstrap<?> bootstrap) {
	}
}
