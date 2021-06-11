package com.github.livingwithhippos.unchained.data.remote

import com.github.livingwithhippos.unchained.data.model.KodiGenericResponse
import retrofit2.Response
import com.github.livingwithhippos.unchained.data.model.KodiRequest
import com.github.livingwithhippos.unchained.data.model.KodiResponse

interface KodiApiHelper {
    suspend fun openUrl(request: KodiRequest): Response<KodiResponse>
    suspend fun getVolume(request: KodiRequest): Response<KodiGenericResponse>
}