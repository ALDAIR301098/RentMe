package com.softgames.rentme.services

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.softgames.rentme.domain.model.FirebaseCodeResponse
import com.softgames.rentme.domain.model.FirebaseCodeResponse.*
import kotlinx.coroutines.channels.awaitClose
import java.util.concurrent.TimeUnit

class PhoneAuthService {
    companion object {

        val auth = Firebase.auth

        fun sendVerificationCode(
            phoneNumber: String,
            activity: Activity,
            onResponse: (FirebaseCodeResponse) -> Unit,
        ) {

            val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    onResponse(AUTOMAMTIC_VERIFICATION(credential))
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
                    super.onCodeSent(verificationId, token)
                    onResponse(ON_CODE_SEND(verificationId, token))
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    onResponse(FAILURE(exception))
                }
            }

            val options = buildOptions(phoneNumber, activity, callback)
            auth.setLanguageCode("es-mx")
            PhoneAuthProvider.verifyPhoneNumber(options)
            onResponse(LOADING)

        }

        private fun buildOptions(
            phone_number: String,
            activity: Activity,
            callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        ): PhoneAuthOptions {
            return PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phone_number)
                .setActivity(activity)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callback)
                .build()
        }

        fun generateCredential(verificationId: String, sms_code: String): PhoneAuthCredential {
            return PhoneAuthProvider.getCredential(verificationId, sms_code)
        }

        fun signInWithPhoneAuthCredential(
            credential: PhoneAuthCredential,
            onSuccess: (task: Task<AuthResult>) -> Unit,
        ) {
            auth.signInWithCredential(credential).addOnCompleteListener { onSuccess(it) }
        }

    }

}