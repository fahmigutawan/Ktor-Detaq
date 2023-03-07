package com.binbraw.data.dao

import com.binbraw.data.dao.user.User
import org.koin.dsl.module

object DaoInjection {
    val provide = module {
        single { User }
    }
}