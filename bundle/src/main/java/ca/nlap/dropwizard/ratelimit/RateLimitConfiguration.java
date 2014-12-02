package ca.nlap.dropwizard.ratelimit;

import redis.clients.jedis.JedisPoolConfig;

public class RateLimitConfiguration {
	JedisPoolConfig poolConfig = new JedisPoolConfig();
	String redisHost;
	int redisPort;

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

}
