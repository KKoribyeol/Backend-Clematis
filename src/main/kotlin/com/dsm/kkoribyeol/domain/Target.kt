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
    @JoinColumn(name = "project_code", referencedColumnName = "code")
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Target

        if (token != other.token) return false
        if (project != other.project) return false
        if (id != other.id) return false
        if (nickname != other.nickname) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = token.hashCode()
        result = 31 * result + project.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + nickname.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }


}