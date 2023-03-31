package com.binbraw.di

import com.binbraw.data.api.emergency_contact.EmContactApi
import com.binbraw.data.api.family.PatientWithFamilyApi
import com.binbraw.data.api.fcm_token.FcmTokenApi
import com.binbraw.data.api.user.UserApi
import com.binbraw.data.api.general.role.RoleApi
import com.binbraw.data.api.reminder.DoctorReminderApi
import com.binbraw.data.api.reminder.MedicineReminderApi
import org.koin.dsl.module

object ApiInjection {
    val provide = module {
        single { UserApi }
        single { RoleApi }
        single { EmContactApi }
        single { MedicineReminderApi }
        single { DoctorReminderApi }
        single { PatientWithFamilyApi }
        single { FcmTokenApi }
    }
}