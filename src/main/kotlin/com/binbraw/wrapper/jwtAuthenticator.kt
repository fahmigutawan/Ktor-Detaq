package com.binbraw.wrapper

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.jwtAuthenticator(apiCall: suspend (String) -> Unit){
    try{
        val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()
        apiCall(uid)
    }catch (e:Exception){
        call.respond(HttpStatusCode.Unauthorized, "Token has been expired, try to re-login")
    }
}