package com.dsm.clematis.domain.account.domain

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

    name: String,
) : UserDetails {

    @Column(name = "name")
    var name = name
        private set

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (password != other.password) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Account(id='$id', password='$password', name='$name')"
    }
}