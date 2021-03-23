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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TargetGroup

        if (project != other.project) return false
        if (groupName != other.groupName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = project.hashCode()
        result = 31 * result + groupName.hashCode()
        return result
    }

    override fun toString(): String {
        return "TargetGroup(project=$project, id=$id, groupName='$groupName')"
    }
}