@file:JvmName("SearchCriteria")
package net.microservices.tutorial.configurationservice.criterias


import org.springframework.util.StringUtils
import org.springframework.validation.Errors

class SearchCriteria {
    var userNumber: Integer? = null

    var surname: String? = null

    var name: String? = null

    fun validate(errors: Errors): Boolean {
        if (userNumber != null) {
            if (StringUtils.hasText(surname)) {
                errors.rejectValue("surname", "nonEmpty",
                        "Cannot specify user number and surname")
            }
            if (StringUtils.hasText(name)) {
                errors.rejectValue("name", "nonEmpty",
                        "Cannot specify user number and name")
            }
        } else {
            if (!StringUtils.hasText(surname)) {
                errors.rejectValue("surname", "empty",
                        "Cannot leave blank user number and surname")
            }
        }
        return errors.hasErrors()
    }

    override fun toString(): String {
        return "SearchCriteria(userNumber=$userNumber, surname=$surname, name=$name)"
    }


}
