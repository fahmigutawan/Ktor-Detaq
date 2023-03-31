package com.binbraw.di

import com.binbraw.data.table.emergency_contact.EmContactTable
import com.binbraw.data.table.emergency_contact.EmContactWithPatientTable
import com.binbraw.data.table.family.PatientWithFamilyTable
import com.binbraw.data.table.fcm_token.FcmTokenTable
import com.binbraw.data.table.general.role.RoleTable
import com.binbraw.data.table.reminder.DoctorReminderTable
import com.binbraw.data.table.reminder.MedicineReminderTable
import com.binbraw.data.table.user.UserTable
import org.koin.dsl.module

object TableInjection {
    val provide = module {
        single { UserTable }
        single { EmContactTable }
        single { EmContactWithPatientTable }
        single { RoleTable }
        single { MedicineReminderTable }
        single { DoctorReminderTable }
        single { PatientWithFamilyTable }
        single { FcmTokenTable }
    }
}