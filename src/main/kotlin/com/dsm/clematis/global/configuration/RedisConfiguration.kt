//package com.dsm.clematis.global.configuration
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.cache.CacheManager
//import org.springframework.cache.annotation.CachingConfigurerSupport
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.cache.RedisCacheManager
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
//
//@Configuration
//@EnableRedisRepositories
//class RedisConfiguration(
//    @Value("\${REDIS_HOST:127.0.0.1}")
//    val redisHost: String,
//    @Value("\${REDIS_HOST:6379}")
//    val redisPort: Int,
//) {
//
//    @Bean
//    fun redisConnectionFactory() = LettuceConnectionFactory(redisHost, redisPort)
//
//    @Bean
//    fun redisTemplate(): RedisTemplate<*, *> {
//        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
//        redisTemplate.setConnectionFactory(redisConnectionFactory())
//        return redisTemplate
//    }
//}