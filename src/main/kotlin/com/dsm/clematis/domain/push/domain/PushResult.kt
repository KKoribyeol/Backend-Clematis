package com.dsm.clematis.domain.push.domain

import com.dsm.clematis.domain.target.domain.Target
import com.dsm.clematis.global.attribute.PushStatus
import com.dsm.clematis.global.converter.PushStatusConverter
import javax.persistence.*

@Entity
@Table(name = "push_result")
class PushResult(

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    val target: Target,

    @ManyToOne
    @JoinColumn(name = "history_id", referencedColumnName = "id")
    val history: PushNotificationHistory,

    status: PushStatus,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "status")
    @Convert(converter = PushStatusConverter::class)
    var status = status
        private set

    fun toComplete() {
        this.status = PushStatus.SUCCESS
    }

    fun toFail() {
        this.status = PushStatus.FAILURE
    }
}