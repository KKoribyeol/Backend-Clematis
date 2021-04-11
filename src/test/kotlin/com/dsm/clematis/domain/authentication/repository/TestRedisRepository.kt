package com.dsm.clematis.domain.authentication.repository

import com.dsm.clematis.domain.authentication.domain.RefreshToken
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
class TestRedisRepository : RedisRepository {
    private val redis = mutableMapOf(
        "savedIdId" to RefreshToken(
            accountId = "savedIdId",
            refreshToken = "this-is-test-token",
        )
    )

    override fun <S : RefreshToken> save(entity: S): S {
        redis[entity.accountId] = entity
        return entity
    }

    override fun deleteById(id: String) {
        redis.remove(id)
    }

    override fun findById(id: String): Optional<RefreshToken> =
        Optional.ofNullable(redis[id])

    override fun <S : RefreshToken?> saveAll(entities: MutableIterable<S>) = listOf<S>()
    override fun existsById(id: String) = true
    override fun findAll() = listOf<RefreshToken>()
    override fun findAllById(ids: MutableIterable<String>) = listOf<RefreshToken>()
    override fun count() = 0L
    override fun delete(entity: RefreshToken) {}
    override fun deleteAll(entities: MutableIterable<RefreshToken>) {}
    override fun deleteAll() {}
}