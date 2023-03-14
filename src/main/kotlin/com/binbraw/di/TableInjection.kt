package com.binbraw.di

import com.binbraw.data.table.emergency_contact.EmContactTable
import com.binbraw.data.table.emergency_contact.EmContactWithPatientTable
import com.binbraw.data.table.user.UserTable
import org.koin.dsl.module

object TableInjection {
    val provide = module {
        single { UserTable }
        single { EmContactTable }
        single { EmContactWithPatientTable }
    }
}