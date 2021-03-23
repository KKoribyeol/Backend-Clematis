package com.dsm.clematis.domain.template.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "template")
@EntityListeners(AuditingEntityListener::class)
class Template(
    title: String,
    body: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "title", length = 40, nullable = false)
    var title = title
        private set

    @Column(name = "body", length = 255, nullable = false)
    var body = body
        private set

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set

    fun modifyContent(title: String, body: String) {
        this.title = title
        this.body = body
    }
}