package com.dsm.kkoribyeol.domain.affiliation.domain

import com.dsm.kkoribyeol.domain.group.domain.TargetGroup
import com.dsm.kkoribyeol.domain.target.domain.Target
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
    @Column(name = "id")
    var id: Long? = null
}