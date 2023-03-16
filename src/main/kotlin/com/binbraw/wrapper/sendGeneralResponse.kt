package com.binbraw.wrapper

import com.binbraw.model.base.GeneralResponse
import com.binbraw.model.base.MetaResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun <T> PipelineContext<Unit, ApplicationCall>.sendGeneralResponse(
    success:Boolean,
    message:String,
    code:HttpStatusCode
) {
    call.respond(
        code,
        GeneralResponse<T>(
            meta = MetaResponse(
                success = success,
                message = message
            ),
            data = null
        )
    )
}

suspend fun <T> PipelineContext<Unit, ApplicationCall>.sendGeneralResponseInsideJWT(
    success:Boolean,
    message:String,
    code:HttpStatusCode
) {
    call.respond(
        code,
        GeneralResponse<T>(
            meta = MetaResponse(
                success = success,
                message = message
            ),
            data = null
        )
    )
}