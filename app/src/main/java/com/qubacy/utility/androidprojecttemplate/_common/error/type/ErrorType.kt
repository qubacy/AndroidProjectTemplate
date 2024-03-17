package com.qubacy.utility.androidprojecttemplate._common.error.type

import com.qubacy.utility.androidprojecttemplate._common.error.domain.ErrorDomain

interface ErrorType{
    val domain: ErrorDomain
    val id: Long

    fun getErrorCode(): Long {
        return domain.id * ErrorDomain.DOMAIN_SIZE + id
    }
}