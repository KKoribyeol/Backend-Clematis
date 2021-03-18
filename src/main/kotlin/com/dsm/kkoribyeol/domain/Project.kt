package com.dsm.kkoribyeol.domain

import javax.persistence.*

@Entity
@Table(name = "project")
class Project(

    @Id
    @Column(name = "code")
    val code: String,

    name: String,
    description: String?,

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    val owner: Account,
) {

    @Column(name = "name")
    var name = name
        private set

    @Column(name = "description")
    var description = description
        private set

    fun modifyContent(name: String?, description: String?) {
        this.name = name ?: this.name
        this.description = description ?: this.description
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Project

        if (code != other.code) return false
        if (owner != other.owner) return false
        if (name != other.name) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
}