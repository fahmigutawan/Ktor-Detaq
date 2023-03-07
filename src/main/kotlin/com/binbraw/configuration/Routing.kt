package com.binbraw.configuration

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureRegularRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

fun Application.configureAuthorizedRouting(){
    routing {
        authenticate("jwt-auth") {

        }
    }
}
