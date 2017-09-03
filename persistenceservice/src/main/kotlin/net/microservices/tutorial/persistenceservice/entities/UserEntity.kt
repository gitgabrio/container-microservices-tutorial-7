@file:JvmName("UserEntity")
package net.microservices.tutorial.persistenceservice.entities


import javax.persistence.*

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 31/10/2016.
 */
@Entity
@Table(name = "persons")
class UserEntity : InterfaceEntity {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "id")
    var id: Int = 0

    @Basic
    @Column(name = "name")
    var name: String = ""

    @Basic
    @Column(name = "surname")
    var surname: String = ""


    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is UserEntity) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false

        return true
    }

    override fun hashCode(): Int{
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        return result
    }

    override fun toString(): String{
        return "UserEntity(id=$id, name=$name, surname=$surname)"
    }


}
