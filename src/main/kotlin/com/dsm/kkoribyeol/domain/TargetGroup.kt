package com.dsm.kkoribyeol.domain

import javax.persistence.*

@Entity
@Table(name = "target_group")
class TargetGroup(

    @Column(name = "name")
    val groupName: String,

    @ManyToOne
    @JoinColumn(name = "project_code", referencedColumnName = "code")
    val project: Project,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null
}