package org.njqspringboot.common.redis;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author 作者 rayping E-mail: leiyongping@nongfadai.com
 * @version 创建时间：2016年9月14日 上午11:06:13 redis基本操作工具类
 */
public class RedisCacheUtil {

	private static RedisTemplate<String, String> redisTemplate = null;
    
    public static void setRedisTemplate(RedisTemplate<String, String> redisTemp) {
        redisTemplate = redisTemp;
    }

	
	///////////////////////////////////////////string/////////////////////////////////////////////////////
	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public static void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public static void removePattern(final String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public static void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public static String get(final String key) {
		String result = null;
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(final String key, String value) {
		boolean result = false;
		try {
			ValueOperations<String, String> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(final String key, String value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<String, String> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 返回获取key且设置value值
	 * @param key
	 * @param value
	 * @return
	 */
	public static Object getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }
	
	/**
	 * 返回keys的结果集
	 * @param keys
	 * @return
	 */
	public static List<String> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

	/**
	 * 指定key递增值
	 * @param key
	 * @param delta
	 * @return
	 */
	public static Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

	/**
	 * 指定key递增值
	 * @param key
	 * @param delta
	 * @return
	 */
    public static Double increment(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
	 * 对key进行append值
	 * @param key
	 * @param delta
	 * @return
	 */
    public static Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

   
    public static String get(String key, long start, long end) {
    	return redisTemplate.opsForValue().get(key, start, end);
    }

    public static void set(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    public static Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }
    
    public static Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    public static Boolean getBit(String key, long offset) {
    	 return redisTemplate.opsForValue().getBit(key, offset);
    }
	///////////////////////////////////////////string/////////////////////////////////////////////////////
	
    
    
    
    ///////////////////////////////////////////key/////////////////////////////////////////////////////
    public static  Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public static void delete(String key) {
    	redisTemplate.delete(key);
    }

    public static void delete(Collection<String> key) {
    	redisTemplate.delete(key);
    }

    public static DataType type(String key) {
        return redisTemplate.type(key);
    }
    
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
    
    /**
	 * 更改key
	 * @param String
	 *            oldkey
	 * @param String
	 *            newkey
	 * @return 状态码
	 * */
	public static boolean rename(String oldKey, String newKey) {
		boolean result = false;
		try {
			redisTemplate.rename(oldKey, newKey);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public static boolean renameIfAbsent(String oldKey, String newKey) {
		boolean result = false;
		try {
			result = redisTemplate.renameIfAbsent(oldKey, newKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean expire(String key, long timeout, TimeUnit unit){
		return redisTemplate.expire(key, timeout, unit);
	}
	
	public static Boolean expireAt(String key, Date date){
		return redisTemplate.expireAt(key, date);
	}
	
	public static Long getExpire(String key){
		return redisTemplate.getExpire(key);
	}
	
	public static Long getExpire(String key, TimeUnit timeUnit){
		return redisTemplate.getExpire(key, timeUnit);
	}
    ///////////////////////////////////////////key/////////////////////////////////////////////////////
}
