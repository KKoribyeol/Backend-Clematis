package com.dsm.kkoribyeol.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "template")
class Template(

    @Column(name = "title", length = 40, nullable = false)
    val title: String,

    @Column(name = "body", length = 255, nullable = false)
    val body: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "create_time")
    var createTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "update_time")
    var updateTime: LocalDateTime = LocalDateTime.now()
}