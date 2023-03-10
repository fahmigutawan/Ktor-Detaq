package com.binbraw.di

import com.binbraw.data.table.general.role.RoleTable
import com.binbraw.data.table.user.UserTable
import org.koin.dsl.module

object TableInjection {
    val provide = module {
        single { UserTable }
        single { RoleTable }
    }
}