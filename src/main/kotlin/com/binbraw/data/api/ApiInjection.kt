package com.binbraw.data.api

import org.koin.dsl.module

object ApiInjection {
    val provide = module{
        UserApi
    }
}