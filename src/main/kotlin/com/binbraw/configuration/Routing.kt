package com.binbraw.configuration

import com.binbraw.data.api.emergency_contact.EmContactApi.addNewEmergencyContact
import com.binbraw.data.api.emergency_contact.EmContactApi.getAllEmergencyContact
import com.binbraw.data.api.emergency_contact.EmContactApi.getEmergencyContactByContactId
import com.binbraw.data.api.general.role.RoleApi.getAllRole
import com.binbraw.data.api.general.role.RoleApi.getRoleById
import com.binbraw.data.api.general.role.RoleApi.newRole
import com.binbraw.data.api.user.UserApi.login
import com.binbraw.data.api.user.UserApi.register
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureRegularRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        register("/user/register")
        login("/user/login")
        newRole("/role/new")
        getAllRole("/role/all")
        getRoleById("/role/getbyid")
    }
}

fun Application.configureAuthorizedRouting(){
    routing {
        authenticate("jwt-auth") {
            addNewEmergencyContact("/emcontact/new")
            getEmergencyContactByContactId("/emcontact/bycontactid")
            getAllEmergencyContact("/emcontact/allemcontact")
        }
    }
}
