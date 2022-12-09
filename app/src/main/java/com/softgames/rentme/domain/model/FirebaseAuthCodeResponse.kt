package com.softgames.rentme.domain.model

import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

sealed class FirebaseCodeResponse {

    object LOADING : FirebaseCodeResponse()

    data class AUTOMAMTIC_VERIFICATION(
        val credential: PhoneAuthCredential,
    ) : FirebaseCodeResponse()

    data class ON_CODE_SEND(
        val verificationId: String,
        val token: PhoneAuthProvider.ForceResendingToken,
    ) : FirebaseCodeResponse()

    data class FAILURE(
        val exception: FirebaseException,
    ) : FirebaseCodeResponse()
}