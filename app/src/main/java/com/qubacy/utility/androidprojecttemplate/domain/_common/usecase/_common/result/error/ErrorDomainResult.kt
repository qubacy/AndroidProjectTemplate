package com.qubacy.utility.androidprojecttemplate.domain._common.usecase._common.result.error

import com.qubacy.utility.androidprojecttemplate._common.error.Error
import com.qubacy.utility.androidprojecttemplate.domain._common.usecase._common.result._common.DomainResult

class ErrorDomainResult(
    val error: Error
) : DomainResult {

}