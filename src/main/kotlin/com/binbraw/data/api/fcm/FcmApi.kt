package com.binbraw.data.api.fcm

import com.binbraw.data.table.family.PatientWithFamilyTable
import com.binbraw.data.table.fcm.FcmTokenTable
import com.binbraw.data.table.user.UserTable
import com.binbraw.model.request.emergency_contact.SendWhatsappRequest
import com.binbraw.model.request.fcm.SendPushNotificationRequest
import com.binbraw.model.request.fcm.SendPushNotificationRequestAsClient
import com.binbraw.model.request.fcm.SendPushNotificationRequestAsClientData
import com.binbraw.model.request.fcm.UpdateFcmTokenRequest
import com.binbraw.util.Config
import com.binbraw.wrapper.sendGeneralResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

object FcmApi: KoinComponent {
    val fcmTokenTable by inject<FcmTokenTable>()
    val patientWithFamilyTable by inject<PatientWithFamilyTable>()
    val userTable by inject<UserTable>()
    val config by inject<Config>()

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

    fun Route.sendPushNotification(path:String){
        post(path){
            val receivedBody = call.receive<SendPushNotificationRequest>()
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }

            val formatLink = "https://www.google.com/maps/search/${receivedBody.latitude},${receivedBody.longitude}"
            val familyIds = transaction {
                patientWithFamilyTable.select {
                    patientWithFamilyTable.patient_id eq UUID.fromString(uid)
                }.mapNotNull {
                    it[patientWithFamilyTable.family_id]
                }
            }

            val patientName = try {
                transaction {
                    userTable.select {
                        userTable.uid eq UUID.fromString(uid)
                    }.mapNotNull {
                        it[userTable.name]
                    }
                }[0]
            }catch (e:Exception){
                "Unknown"
            }

            familyIds.forEach { family_id ->
                val token = try{
                    transaction {
                        fcmTokenTable.select {
                            fcmTokenTable.uid eq UUID.fromString(family_id)
                        }.mapNotNull {
                            it[fcmTokenTable.token]
                        }
                    }[0]
                }catch (e:Exception){
                    ""
                }

                client.post("https://fcm.googleapis.com/fcm/send"){
                    header("Authorization", "key=${config.fcm_access_key}")
                    setBody(SendPushNotificationRequestAsClient(
                        to = token,
                        notification = SendPushNotificationRequestAsClientData(
                            body = "Something happens to $patientName, please check or click this notification to be redirected to Google Maps",
                            title = "SOS Notification from your family",
                            maps_link = formatLink
                        )
                    ))
                    contentType(ContentType.Application.Json)
                }
            }

            sendGeneralResponse<Any>(
                success = true,
                message = "Send notification succeeded",
                code = HttpStatusCode.OK
            )
        }
    }
}