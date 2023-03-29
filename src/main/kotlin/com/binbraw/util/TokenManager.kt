package com.binbraw.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object TokenManager : KoinComponent {
    val config by inject<Config>()
    private val algorithm = Algorithm.HMAC256(config.jwt_secret)
    private val verifier = JWT
        .require(algorithm)
        .withIssuer(config.jwt_issuer)
        .build()

    fun generateJwtToken(uid: String) =
        JWT.create()
            .withIssuer(config.jwt_issuer)
            .withAudience(config.jwt_audience)
            .withClaim("uid", uid)
            .sign(algorithm)

    fun configureKtorFeature(conf:JWTAuthenticationProvider.Config) = with(conf){
        verifier(verifier)
        realm = config.jwt_realm
        validate { cred ->
            if(cred.payload.getClaim("uid").asString() != ""){
                JWTPrincipal(cred.payload)
            }else{
                null
            }
        }
    }
}