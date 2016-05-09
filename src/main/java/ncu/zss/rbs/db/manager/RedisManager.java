package ncu.zss.rbs.db.manager;

import ncu.zss.rbs.util.PropertiesManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {
	
	private static final String redisPropertiesFile = "redis.properties";
	
	private String redisHost = "";
	private String redisPort = "";
	private String redisPw = "";
	
	// redis pool
	private JedisPool jedisPool;
	
	// Redis DBs.
	public static final int DB_ADMIN = 1;
	public static final int DB_USER = 2;
	public static final int DB_ROOM_LIST = 3;
	
	// Default expire time.
	public static final int EXPIRE_TIME = 7 * 24 * 3600;
	
	private RedisManager() {
		
		this.redisHost = PropertiesManager.getInstance().getValue(redisPropertiesFile, "redis_localhost");
		this.redisPort = PropertiesManager.getInstance().getValue(redisPropertiesFile, "redis_port");
		this.redisPw = PropertiesManager.getInstance().getValue(redisPropertiesFile, "redis_password");
		
		initPool();
	}
	
	private void initPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(240);
		config.setMaxIdle(200);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(false);
		
		jedisPool = new JedisPool(config, redisHost, Integer.valueOf(redisPort));
	}
	
	public static RedisManager getInstance() {
		return Instance.rm;
	}
	
	public static class Instance {
		private static RedisManager rm = new RedisManager();
	}
	
	/**
	 * get jedis instance
	 * @return
	 */
	public Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redisPw);
		return jedis;
	}
	
	/**
	 * return jedis to the jedispool
	 * @param jedis
	 */
	public void returnJedis(Jedis jedis) {
		if(jedis != null) {
			jedis.close();
		}
	}
	
	/**
	 * store 
	 * @param db
	 * @param key
	 * @param value
	 * @throws expire
	 */
	public static void storeValueInRedis(int db, String key, String value, int expire) {
		Jedis jedis = null;
		try {
			jedis = getInstance().getJedis();
			jedis.select(db);
			jedis.set(key, value);
			if (expire > -1) {
				jedis.expire(key, expire);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			getInstance().returnJedis(jedis);
		}
	}
	
	/**
	 * get vaule which tye is string
	 * @param db
	 * @param key
	 * @return
	 * @throws AiException
	 */
	public static String getStringValueRedis(int db, String key) {
		Jedis jedis = null;
		try {
			jedis = getInstance().getJedis();
			jedis.select(db);
			return jedis.get(key);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			getInstance().returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 
	 * @param db
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void storeMapInRedis(int db, String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = getInstance().getJedis();
			jedis.select(db);
			jedis.hset(key, field, value);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			getInstance().returnJedis(jedis);
		}
	}
	
	/**
	 * get value from map by key and field
	 * @param db
	 * @param key
	 * @param field
	 * @return
	 */
	public static String getValueFromMap(int db, String key, String field) {
		Jedis jedis = null;
		try {
			jedis = getInstance().getJedis();
			jedis.select(db);
			return jedis.hget(key, field);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			getInstance().returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * del value from redis
	 * @param db
	 * @param key
	 * @param field
	 */
	public static void removeValueFromRedis(int db, String key, String field) {
		Jedis jedis = null;
		try {
			jedis = getInstance().getJedis();
			jedis.select(db);
			jedis.hdel(key, field);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			getInstance().returnJedis(jedis);
		}
	}
	
	public static void flushDB(int db) {
		Jedis jedis = null;
		try {
			jedis = getInstance().getJedis();
			jedis.select(db);
			jedis.flushDB();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			getInstance().returnJedis(jedis);
		}
	}

}
