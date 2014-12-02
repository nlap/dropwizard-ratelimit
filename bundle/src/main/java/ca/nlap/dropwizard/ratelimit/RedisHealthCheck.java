package ca.nlap.dropwizard.ratelimit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.codahale.metrics.health.HealthCheck;

public class RedisHealthCheck extends HealthCheck {
	private JedisPool jedisPool;
	
	public RedisHealthCheck(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	protected Result check() throws Exception {
		Jedis jedis = jedisPool.getResource();
		try {
			String reply = jedis.ping();
			jedisPool.returnResource(jedis);
			if (!reply.equalsIgnoreCase("PONG")) {
				return Result.unhealthy("Redis did not respond to ping");
			}
		} catch(Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return Result.unhealthy(e.getMessage());
		}
		return Result.healthy();
	}
}
