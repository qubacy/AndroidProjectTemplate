package com.qubacy.utility.androidprojecttemplate._common.exception.error

import com.qubacy.utility.androidprojecttemplate._common.error.Error
import com.qubacy.utility.androidprojecttemplate._common.exception._common.AppException

data class ErrorAppException(
    val error: Error
) : AppException() {

}