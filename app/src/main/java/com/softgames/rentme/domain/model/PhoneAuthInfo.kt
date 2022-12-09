package com.softgames.rentme.domain.model

import com.google.firebase.auth.PhoneAuthProvider

data class PhoneAuthInfo(
    var codeWasSend: Boolean = false,
    var verificationId: String? = null,
    var token: PhoneAuthProvider.ForceResendingToken? = null
)