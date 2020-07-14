package com.github.livingwithhippos.unchained.base.model.repositories

import com.github.livingwithhippos.unchained.authentication.model.Authentication
import com.github.livingwithhippos.unchained.authentication.model.Secrets
import com.github.livingwithhippos.unchained.authentication.model.Token
import com.github.livingwithhippos.unchained.base.model.network.AuthApiHelper
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val apiHelper: AuthApiHelper) :
    BaseRepository() {

    suspend fun getVerificationCode(): Authentication? {

        val authResponse = safeApiCall(
            call = { apiHelper.getAuthentication() },
            errorMessage = "Error Fetching Authentication Info"
        )

        return authResponse;

    }

    suspend fun getSecrets(code: String): Secrets? {

        val authResponse = safeApiCall(
            call = { apiHelper.getSecrets(deviceCode = code) },
            errorMessage = "Error Fetching Secrets"
        )

        return authResponse;

    }

    suspend fun getToken(clientId: String, clientSecret: String, deviceCode: String): Token? {

        val authResponse = safeApiCall(
            call = {
                apiHelper.getToken(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    deviceCode = deviceCode
                )
            },
            errorMessage = "Error Fetching Token"
        )

        return authResponse;

    }
}