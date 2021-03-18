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
}