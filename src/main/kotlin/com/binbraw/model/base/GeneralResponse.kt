package com.binbraw.model.base

import kotlinx.serialization.Serializable

@Serializable
data class GeneralResponse <T>(
    val meta:MetaResponse,
    val data:T?
)
