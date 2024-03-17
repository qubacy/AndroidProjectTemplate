package com.qubacy.utility.androidprojecttemplate._common.error.type

import com.qubacy.utility.androidprojecttemplate._common.error.domain.ErrorDomain

enum class TestErrorType(
    override val id: Long,
    override val domain: ErrorDomain = ErrorDomain.COMMON
) : ErrorType {
    TEST(0);
}