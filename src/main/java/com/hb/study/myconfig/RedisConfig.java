package com.hb.study.myconfig;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author huangbing
 * @date 2020/04/10 17:47
 */
@Configuration
public class RedisConfig  {
    /**
     * 缓存自动配置类:CacheAutoConfiguration通过@Import注解给容器中添加了CacheConfigurationImportSelector类
     * 通过该类中selectImports方法给容器中注册缓存配置类
     * springboot默认使用的缓存配置类:SimpleCacheConfiguration
     * 该配置类中给容器中添加了一个缓存管理器:ConcurrentMapCacheManager
     * 其实就是通过这个对象去管理缓存,key为缓存名字,value为缓存对象,private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
     * 可以通过该类getCache方法获取一个缓存,如果该缓存不存在,则通过createConcurrentMapCache方法创建一个缓存对象(ConcurrentMapCache)
     * 所谓的缓存就是一个 private final ConcurrentMap<Object, Object> store = new ConcurrentHashMap<>(256)对象;
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jsonSerializer);
        RedisCacheConfiguration defaultCacheConfig= RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair);
        //设置默认超过期时间是30秒
//         defaultCacheConfig.entryTtl(Duration.ofSeconds(30));
        //初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }
}
