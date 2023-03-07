package com.binbraw.configuration

import com.binbraw.util.TokenManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthorization(){
    install(Authentication){
        jwt(name = "jwt-auth"){
            TokenManager.configureKtorFeature(this)
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token has been expired. Try to re-login")
            }
        }
    }
}