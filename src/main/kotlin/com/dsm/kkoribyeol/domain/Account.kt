package com.dsm.kkoribyeol.domain

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "account")
class Account(

    @Id @Column(name = "id")
    val id: String,

    @Column(name = "password")
    private var password: String,

    @Column(name = "name")
    private var name: String,
) : UserDetails {

    fun modifyPassword(newPassword: String) {
        this.password = newPassword
    }

    fun modifyName(newName: String) {
        this.name = newName
    }

    override fun getAuthorities() = mutableListOf<SimpleGrantedAuthority>()

    override fun getPassword() = password

    override fun getUsername() = id

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}