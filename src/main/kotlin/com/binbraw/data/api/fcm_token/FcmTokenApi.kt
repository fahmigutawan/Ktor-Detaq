package com.binbraw.data.api.fcm_token

import com.binbraw.data.table.fcm_token.FcmTokenTable
import com.binbraw.model.request.fcm_token.UpdateFcmTokenRequest
import com.binbraw.wrapper.sendGeneralResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

object FcmTokenApi: KoinComponent {
    val fcmTokenTable by inject<FcmTokenTable>()

    fun Route.updateFcmToken(path:String){
        post(path){
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()
            val body = call.receive<UpdateFcmTokenRequest>()

            transaction {
                fcmTokenTable.update({
                    fcmTokenTable.uid eq UUID.fromString(uid)
                }) {
                    it[fcmTokenTable.token] = body.fcm_token
                }
            }.let {
                sendGeneralResponse<Any>(
                    success = true,
                    message = "FcmToken updated",
                    code = HttpStatusCode.OK
                )
            }
        }
    }
}