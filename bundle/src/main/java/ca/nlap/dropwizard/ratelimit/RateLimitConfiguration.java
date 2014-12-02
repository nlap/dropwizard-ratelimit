package ca.nlap.dropwizard.ratelimit;

import redis.clients.jedis.JedisPoolConfig;

public class RateLimitConfiguration {
	JedisPoolConfig poolConfig = new JedisPoolConfig();
	String host;
	int port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxIdle() {
		return poolConfig.getMaxIdle();
	}

	public void setMaxIdle(int maxIdle) {
		poolConfig.setMaxIdle(maxIdle);
	}

	public int getMinIdle() {
		return poolConfig.getMinIdle();
	}

	public void setMinIdle(int minIdle) {
		poolConfig.setMinIdle(minIdle);
	}
}
