package com.qubacy.utility.androidprojecttemplate._common.error._test

import com.qubacy.utility.androidprojecttemplate._common.error.Error
import com.qubacy.utility.androidprojecttemplate._common.error.type.TestErrorType

object TestError {
    val normal = Error(
        TestErrorType.TEST.id, "normal fake error", false
    )
    val critical = Error(
        TestErrorType.TEST.id, "critical fake error", true
    )
}