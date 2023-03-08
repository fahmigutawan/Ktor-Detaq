package com.binbraw.model.base

import kotlinx.serialization.Serializable

@Serializable
data class MetaResponse(
    val success:Boolean,
    val message:String
)
