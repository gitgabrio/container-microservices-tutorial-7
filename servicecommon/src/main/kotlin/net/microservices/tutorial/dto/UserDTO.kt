package net.microservices.tutorial.dto


/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 27/05/17.
 */
class UserDTO() : InterfaceDTO {

    var id: Int? = 0
    var name : String = ""
    var surname : String = ""

    constructor(id: Int?,  name : String,  surname : String) : this() {
        this.id = id
        this.name = name
        this.surname = surname
    }

    override fun toString(): String{
        return "UserDTO(id=$id, name='$name', surname='$surname')"
    }
}