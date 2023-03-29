package com.binbraw.model.base

import kotlinx.serialization.Serializable

data class GeneralResponseWithId(
    val meta:MetaResponse,
    val data:GeneralResponseWithIdData
)
@Serializable
data class GeneralResponseWithIdData(
    val created_id_related:String
)
