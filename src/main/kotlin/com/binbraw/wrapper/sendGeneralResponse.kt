package com.binbraw.wrapper

import com.binbraw.model.base.GeneralResponse
import com.binbraw.model.base.MetaResponse
import com.binbraw.model.base.GeneralResponseWithId
import com.binbraw.model.base.GeneralResponseWithIdData
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

suspend fun PipelineContext<Unit, ApplicationCall>.sendGeneralResponseWithId(
    success:Boolean,
    message:String,
    code:HttpStatusCode,
    idRelated:String
) {
    call.respond(
        code,
        GeneralResponseWithId(
            meta = MetaResponse(
                success = success,
                message = message
            ),
            data = GeneralResponseWithIdData(
                created_id_related = idRelated
            )
        )
    )
}