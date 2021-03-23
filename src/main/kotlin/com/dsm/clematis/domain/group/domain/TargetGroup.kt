package com.dsm.clematis.domain.group.domain

import com.dsm.clematis.domain.project.domain.Project
import javax.persistence.*

@Entity
@Table(name = "target_group")
class TargetGroup(

    groupName: String,

    @ManyToOne
    @JoinColumn(name = "project_code", referencedColumnName = "code")
    val project: Project,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "name")
    var groupName = groupName

    fun modifyName(newGroupName: String) {
        this.groupName = newGroupName
    }
}