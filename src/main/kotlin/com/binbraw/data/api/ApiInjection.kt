package com.binbraw.data.api

import com.binbraw.data.api.user.UserApi
import org.koin.dsl.module

object ApiInjection {
    val provide = module{
        single{ UserApi }
    }
}