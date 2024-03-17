package com.qubacy.utility.androidprojecttemplate._common.exception._common

abstract class AppException(
    message: String? = null,
    cause: Throwable? = null
): Exception(message, cause) {

}