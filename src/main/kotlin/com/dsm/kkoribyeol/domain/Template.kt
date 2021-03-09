package com.dsm.kkoribyeol.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "template")
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

    @Column(name = "create_time")
    var createTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "update_time")
    var updateTime: LocalDateTime = LocalDateTime.now()

    fun modifyContent(title: String, body: String) {
        this.title = title
        this.body = body
    }
}