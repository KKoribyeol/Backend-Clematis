package com.dsm.clematis.domain.affiliation.domain

import com.dsm.clematis.domain.group.domain.TargetGroup
import com.dsm.clematis.domain.target.domain.Target
import javax.persistence.*

@Entity
@Table(name = "affiliation")
class TargetAffiliation(

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    val target: Target,

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    val group: TargetGroup,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TargetAffiliation

        if (target != other.target) return false
        if (group != other.group) return false

        return true
    }

    override fun hashCode(): Int {
        var result = target.hashCode()
        result = 31 * result + group.hashCode()
        return result
    }

    override fun toString(): String {
        return "TargetAffiliation(target=$target, group=$group, id=$id)"
    }
}