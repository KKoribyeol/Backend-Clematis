package com.dsm.kkoribyeol.domain

import javax.persistence.*

@Entity
@Table(name = "target")
class Target(

    @Column(name = "token")
    val token: String,

    nickname: String,
    name: String?,

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "code")
    val project: Project,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "nickname", unique = true)
    var nickname = nickname
        private set

    @Column(name = "name")
    var name = name
        private set

    fun modifyContent(nickname: String?, name: String?) {
        this.nickname = nickname ?: this.nickname
        this.name = name ?: this.name
    }
}