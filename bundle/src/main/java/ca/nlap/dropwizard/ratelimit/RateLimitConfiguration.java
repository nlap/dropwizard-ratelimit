package ca.nlap.dropwizard.ratelimit;

import redis.clients.jedis.JedisPoolConfig;

public class RateLimitConfiguration {

	private JedisPoolConfig poolConfig = new JedisPoolConfig();
	private String redisHost;
	private int redisPort;

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

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

}
