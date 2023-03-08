package com.binbraw.configuration

import com.binbraw.data.api.user.UserApi
import com.binbraw.data.api.user.UserApi.register
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureRegularRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        register()
    }
}

fun Application.configureAuthorizedRouting(){
    routing {
//        authenticate("jwt-auth") {
//
//        }
    }
}
