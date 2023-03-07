package com.binbraw.di

import com.binbraw.data.dao.user.UserDao
import org.koin.dsl.module

object DaoInjection {
    val provide = module {
        single { UserDao }
    }
}