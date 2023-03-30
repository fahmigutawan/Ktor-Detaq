package com.binbraw.configuration

import com.binbraw.data.api.emergency_contact.EmContactApi.addNewEmergencyContact
import com.binbraw.data.api.emergency_contact.EmContactApi.getAllEmergencyContact
import com.binbraw.data.api.emergency_contact.EmContactApi.getEmergencyContactByContactId
import com.binbraw.data.api.family.PatientWithFamilyApi.addNewFamily
import com.binbraw.data.api.general.role.RoleApi.getAllRole
import com.binbraw.data.api.general.role.RoleApi.getRoleById
import com.binbraw.data.api.general.role.RoleApi.newRole
import com.binbraw.data.api.reminder.DoctorReminderApi.addNewDoctorReminder
import com.binbraw.data.api.reminder.DoctorReminderApi.getAllDoctorReminder
import com.binbraw.data.api.reminder.MedicineReminderApi.addNewMedicineReminder
import com.binbraw.data.api.reminder.MedicineReminderApi.getAllMedicineReminder
import com.binbraw.data.api.user.UserApi.getMyOwnUserInfo
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
            getMyOwnUserInfo("/user/myuser")
            addNewEmergencyContact("/emcontact/new")
            getEmergencyContactByContactId("/emcontact/bycontactid")
            getAllEmergencyContact("/emcontact/allemcontact")
            addNewMedicineReminder("/med_reminder/add")
            getAllMedicineReminder("/med_reminder/all")
            addNewDoctorReminder("/doc_reminder/add")
            getAllDoctorReminder("/doc_reminder/all")
            addNewFamily("/family/add")
        }
    }
}
