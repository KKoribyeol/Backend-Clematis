package com.dsm.kkoribyeol.domain

import javax.persistence.*

@Entity
@Table(name = "project")
class Project(

    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String?,

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    val owner: Account,
)