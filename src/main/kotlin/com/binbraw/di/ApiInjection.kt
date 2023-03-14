package com.binbraw.di

import com.binbraw.data.api.emergency_contact.EmContactApi
import com.binbraw.data.api.user.UserApi
import org.koin.dsl.module

object ApiInjection {
    val provide = module {
        single { UserApi }
        single { EmContactApi }
    }
}