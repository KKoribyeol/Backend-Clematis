package com.dsm.clematis.domain.push.domain

import com.dsm.clematis.domain.project.domain.Project
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "push_notification_history")
@EntityListeners(AuditingEntityListener::class)
class PushNotificationHistory(

    @Column(name = "title")
    val title: String,

    @Column(name = "body")
    val body: String,

    @ManyToOne
    @JoinColumn(name = "project_code", referencedColumnName = "code")
    val project: Project,

    @Column(name = "reserved_at")
    val reservedAt: LocalDateTime?,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @Column(name = "completed_at")
    var completedAt: LocalDateTime? = null
        private set

    @OneToMany(mappedBy = "history")
    val pushResults: List<PushResult> = listOf()

    fun complete() {
        this.completedAt = LocalDateTime.now()
    }
}