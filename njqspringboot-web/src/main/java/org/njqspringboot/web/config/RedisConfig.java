package org.njqspringboot.web.config;

import java.lang.reflect.Method;

import org.njqspringboot.common.redis.RedisCacheUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
* @author 作者 rayping E-mail: leiyongping@nongfadai.com
* @version 创建时间：2016年9月14日 上午10:15:17 
* redis配置类 
*/
@Configuration
@EnableCaching
public class RedisConfig {
	
	/**
	 * 自定义缓存数据 key 生成策略
	 */
	@Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {

			@Override
			public Object generate(Object target, Method method, Object... params) {
				 StringBuilder sb = new StringBuilder();
		         sb.append(target.getClass().getName());
		         sb.append(method.getName());
		         for (Object obj : params) {
		             sb.append(obj.toString());
		         }
		         return sb.toString();
			}
           
        };
    }
	
	/**
	 * RedisTemplate配置
	 * 
	 *  JdkSerializationRedisSerializer：使用JDK的序列化手段(serializable接口，ObjectInputStrean，ObjectOutputStream)，数据以字节流存储
	 *	StringRedisSerializer：字符串编码，数据以string存储
	 *	JacksonJsonRedisSerializer：json格式存储
	 *	OxmSerializer：xml格式存储
	 * GenericToStringSerializer
	 * @return
	 */
	@Bean
    public StringRedisTemplate template(RedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class); 
        ObjectMapper om = new ObjectMapper();  
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);

        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setKeySerializer(new StringRedisSerializer());   //对于普通K-V操作时，key采取的序列化策略
        template.setValueSerializer(new StringRedisSerializer());      //value采取的序列化策略
        
        template.setHashKeySerializer(serializer);  //在hash数据结构中，hash-key的序列化策略
        template.setHashValueSerializer(serializer);   //在hash数据结构中，hash-key的序列化策略
        template.afterPropertiesSet(); 
        
        RedisCacheUtil.setRedisTemplate(template);
        return template;
    }
	
	/**
	 * 设置缓存对象的序列化方式
	 * 另外对于json序列化,对象要提供默认空构造器
	 * @param redisTemplate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		//defaultExpireTime time in seconds.
		cacheManager.setDefaultExpiration(300);
		return cacheManager;
	}
}
