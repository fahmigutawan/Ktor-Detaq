package com.binbraw.di

import com.binbraw.data.api.general.role.RoleApi
import com.binbraw.data.api.user.UserApi
import org.koin.dsl.module

object ApiInjection {
    val provide = module{
        single{ UserApi }
        single{ RoleApi }
    }
}